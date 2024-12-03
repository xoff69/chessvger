package main

import (
	"context"
	"fmt"
	"github.com/segmentio/kafka-go"
	"log"
	"time"
)

const topic = "quickstart-events-25"

func creer(ctx context.Context, broker string) {
	// Adresse du broker Kafka
	brokerAddress := broker

	// Nom du topic à créer
	topic := topic

	// Nombre de partitions et facteur de réplication
	numPartitions := 3
	replicationFactor := 1

	// Connexion au broker Kafka
	conn, err := kafka.Dial("tcp", brokerAddress)
	if err != nil {
		log.Fatalf("Erreur lors de la connexion au broker Kafka : %v", err)
	}
	defer conn.Close()

	// Contexte avec délai pour éviter les blocages
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	// Création du topic
	err = conn.CreateTopics(kafka.TopicConfig{
		Topic:             topic,
		NumPartitions:     numPartitions,
		ReplicationFactor: replicationFactor,
	})
	if err != nil {
		log.Fatalf("Erreur lors de la création du topic : %v", err)
	}

	fmt.Printf("Topic '%s' créé avec succès !\n", topic)
}
func checkKafka(ctx context.Context, broker string) {
	//brokerAddress := "localhost:19092" // Remplacez par l'adresse de votre broker Kafka
	brokerAddress := broker
	// Création d'un lecteur Kafka uniquement pour récupérer des métadonnées
	conn, err := kafka.Dial("tcp", brokerAddress)
	if err != nil {
		log.Fatalf("Impossible de se connecter au broker Kafka : %v", err)
	}
	defer conn.Close()

	// Définir un délai pour éviter les blocages
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	// Obtenir la liste des topics
	partitions, err := conn.ReadPartitions()
	if err != nil {
		log.Fatalf("Erreur lors de la lecture des partitions : %v", err)
	}

	// Utiliser un map pour éviter les doublons (un topic peut avoir plusieurs partitions)
	topics := make(map[string]struct{})
	for _, p := range partitions {
		topics[p.Topic] = struct{}{}
	}

	// Afficher les topics
	fmt.Println("Liste des topics Kafka :")
	for topic := range topics {
		fmt.Println(topic)
	}
}
func envoie(broker string) {
	// Adresse du broker Kafka
	brokers := []string{broker}

	// Création d'un writer Kafka
	writer := kafka.Writer{
		Addr:         kafka.TCP(brokers...), // Adresse des brokers
		Topic:        topic,                 // Nom du topic
		Balancer:     &kafka.LeastBytes{},   // Algorithme d'équilibrage
		RequiredAcks: kafka.RequireOne,      // Attente de confirmation
	}

	// Message à envoyer
	message := kafka.Message{
		Key:   []byte("Key-A"),                    // Clé du message (optionnelle)
		Value: []byte("Hello, Kafka!:::" + topic), // Contenu du message
	}

	// Publication du message
	err := writer.WriteMessages(context.Background(), message)
	if err != nil {
		log.Fatalf("Erreur lors de l'envoi du message : %v", err)
	}

	fmt.Println("Message envoyé avec succès !")

	// Fermer le writer après usage
	if err := writer.Close(); err != nil {
		log.Fatalf("Erreur lors de la fermeture du writer : %v", err)
	}
}
func recoitBoucleInfinie(broker string) {
	// Adresse du broker Kafka
	brokers := []string{broker}

	// Nom du groupe de consommateurs
	groupID := "example-group"

	// Création d'un lecteur Kafka
	reader := kafka.NewReader(kafka.ReaderConfig{
		Brokers:  brokers,         // Adresse des brokers
		Topic:    topic,           // Nom du topic
		GroupID:  groupID,         // ID du groupe de consommateurs
		MinBytes: 10e3,            // Taille minimale du message à lire (10KB)
		MaxBytes: 10e6,            // Taille maximale du message à lire (10MB)
		MaxWait:  1 * time.Second, // Temps maximum d'attente pour un message
	})

	fmt.Println("Consommateur Kafka démarré...")

	// Boucle pour lire les messages
	for {
		msg, err := reader.ReadMessage(context.Background())
		if err != nil {
			log.Fatalf("Erreur lors de la lecture du message : %v", err)
		}
		fmt.Printf("Message reçu : key=%s value=%s offset=%d partition=%d\n",
			string(msg.Key), string(msg.Value), msg.Offset, msg.Partition)
	}

	// Fermer le lecteur (si vous quittez la boucle)
	if err := reader.Close(); err != nil {
		log.Fatalf("Erreur lors de la fermeture du reader : %v", err)
	}
}
