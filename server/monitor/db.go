package main

import (
	"database/sql"
	"fmt"
	"log"
)

// ************** SQL
// Connexion à PostgreSQL
func execSQLTTT(dbserver string) {

	fmt.Println("execSQLTTT créé avec succès." + dbserver)

	connStr := "postgres://chessvger:chessvger@" + dbserver + "/chessvger?sslmode=disable"
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		log.Fatalf("Erreur de connexion à la base de données : %v", err)
	}
	defer db.Close()

	var count int
	err = db.QueryRow("SELECT COUNT(*) FROM chessvger.common_player").Scan(&count)
	if err != nil {
		log.Fatalf("Erreur lors de la lecture du nombre d'enregistrements : %v", err)
	}
	fmt.Printf("Nombre total d'enregistrements dans chessvger.common_player: %d\n", count)
}
