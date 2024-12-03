package main

import (
	"context"
	"fmt"
	"github.com/go-redis/redis/v8"
	"log"
	"strconv"
)

func checkRedis(ctx context.Context, redisServer string) {
	// Configurer la connexion Redis
	rdb := redis.NewClient(&redis.Options{
		Addr:     redisServer, // Adresse de votre instance Redis
		Password: "",          // Mot de passe (si configuré)
		DB:       0,           // Base de données Redis
	})

	// Vérifier la connexion
	_, err := rdb.Ping(ctx).Result()
	if err != nil {
		log.Println("Impossible de se connecter à Redis : %v", err)
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
		log.Println("Erreur lors de la vérification de la clé : %v", err)
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

}
