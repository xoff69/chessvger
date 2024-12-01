package main

import (
	"context"
	"database/sql"
	"fmt"
	"log"
	"strconv"
	"time"

	"github.com/go-redis/redis/v8"
	_ "github.com/lib/pq" // Import du pilote PostgreSQL
	"github.com/segmentio/kafka-go"
)

const (
	connStr       = "user=chessvger password=chessvger dbname=postgres sslmode=disable host=db_chessvger port=5432"
	brokerAddress = "kafka:9092" // Remplacez par l'adresse de votre broker Kafka
)

func checkKafka(ctx context.Context) {

	// Création d'un lecteur Kafka uniquement pour récupérer des métadonnées
	conn, err := kafka.Dial("tcp", brokerAddress)
	if err != nil {
		log.Printf("Impossible de se connecter au broker Kafka : %v", err)
		return
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
	fmt.Println("Liste des topics Kafka : fin")
}
func checkDb(ctx context.Context) {

	// Connexion à la base de données
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		log.Printf("Erreur lors de la connexion à PostgreSQL : %v", err)
		return
	}
	defer db.Close()

	// Vérifier la connexion
	err = db.Ping()
	if err != nil {
		log.Printf("aaa Impossible de se connecter à la base de données : %v", err)
		return
	}
	fmt.Println("Connexion réussie à PostgreSQL.")
	// Créer une nouvelle base de données
	dbName := "chessvger"
	_, err = db.Exec(fmt.Sprintf("CREATE schema if not exists  %s", dbName))
	if err != nil {
		log.Printf("Le schema %s existe  déjà : %v", dbName, err)
	} else {
		log.Printf("schemas '%s' créée avec succès.", dbName)
	}

	// Requête pour lister les schémas
	query := `
       SELECT schema_name
       FROM information_schema.schemata
       ORDER BY schema_name;
    `

	rows, err := db.Query(query)
	if err != nil {
		log.Fatalf("Erreur lors de l'exécution de la requête : %v", err)
	}
	defer rows.Close()

	// Parcourir les résultats
	fmt.Println("Liste des schémas :")
	for rows.Next() {
		var schemaName string
		if err := rows.Scan(&schemaName); err != nil {
			log.Fatalf("Erreur lors du scan des résultats : %v", err)
		}
		fmt.Println(schemaName)
	}

	// Vérifier les erreurs après le parcours
	if err = rows.Err(); err != nil {
		log.Fatalf("Erreur lors du parcours des résultats : %v", err)
	}
}
func checkRedis(ctx context.Context) {
	// Configurer la connexion Redis
	rdb := redis.NewClient(&redis.Options{
		Addr:     "redis_container:6379", // Adresse de votre instance Redis
		Password: "",                     // Mot de passe (si configuré)
		DB:       0,                      // Base de données Redis
	})

	// Vérifier la connexion
	_, err := rdb.Ping(ctx).Result()
	if err != nil {
		log.Printf("Impossible de se connecter à Redis : %v", err)
		return
	}

	// Clé à vérifier
	key := "example:key"

	rdb.Set(ctx, key, "toto", 0)
	for i := 0; i < 10; i++ {
		rdb.Set(ctx, key+":"+strconv.Itoa(i), "toto"+strconv.Itoa(i), 0)
	}

	// Vérifier si la clé existe
	exists, err := rdb.Exists(ctx, key).Result()
	if err != nil {
		log.Fatalf("Erreur lors de la vérification de la clé : %v", err)
	}

	if exists == 0 {
		fmt.Printf("La clé '%s' n'existe pas dans Redis.\n", key)
	} else {
		// Récupérer la valeur de la clé
		value, err := rdb.Get(ctx, key).Result()
		if err != nil {
			log.Fatalf("Erreur lors de la récupération de la clé : %v", err)
		}
		fmt.Printf("La clé '%s' existe avec la valeur : %s\n", key, value)
	}

	// Récupérer toutes les clés
	keys, err := rdb.Keys(ctx, "*").Result()
	if err != nil {
		log.Fatalf("Erreur lors de la récupération des clés : %v", err)
	}

	// Afficher les clés
	fmt.Println("Clés disponibles :")
	for _, key := range keys {
		fmt.Println(key + ":" + rdb.Get(ctx, key).String())
	}
}
func main() {
	ctx := context.Background()
	checkRedis(ctx)
	checkDb(ctx)
	checkKafka(ctx)
}
