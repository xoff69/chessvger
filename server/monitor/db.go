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

	connStr := "postgres://chessvger:chessvger@" + dbserver + "/chessvger?sslmode=disable" // Remplacez avec vos infos
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		log.Fatalf("Erreur de connexion à la base de données : %v", err)
	}
	defer db.Close()

	// Création de la table
	_, err = db.Exec(`
       CREATE TABLE IF NOT EXISTS chessvger.my_table (
          id SERIAL PRIMARY KEY,
          name VARCHAR(50),
          age INT
       )
    `)
	if err != nil {
		log.Fatalf("Erreur lors de la création de la table : %v", err)
	}
	fmt.Println("Table créée avec succès.")

	// Insertion de données
	_, err = db.Exec(`
       INSERT INTO chessvger.my_table (name, age) VALUES
       ('Alice', 25),
       ('Bob', 30),
       ('Charlie', 35)
       ON CONFLICT DO NOTHING
    `)
	if err != nil {
		log.Fatalf("Erreur lors de l'insertion des enregistrements : %v", err)
	}
	fmt.Println("Enregistrements insérés avec succès.")

	// Lecture du nombre d'enregistrements
	var count int
	err = db.QueryRow("SELECT COUNT(*) FROM chessvger.my_table").Scan(&count)
	if err != nil {
		log.Fatalf("Erreur lors de la lecture du nombre d'enregistrements : %v", err)
	}
	fmt.Printf("Nombre total d'enregistrements : %d\n", count)
}
