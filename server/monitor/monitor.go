package main

import (
	"context"
	"fmt"
	_ "github.com/lib/pq" // Import du pilote PostgreSQL
	"os"
)

/**
* go run monitor.go toto
 */
func main() {
	ctx := context.Background()

	dbserver := "localhost:5432"
	redisServer := "localhost:6379"
	if len(os.Args) > 1 {

		env := os.Args[1]
		fmt.Println("env=" + env)
		if env == "cluster" {
			dbserver = "postgres:5432"
			redisServer = "redis:6379"
		}
	}
	execSQLTTT(dbserver)
	checkRedis(ctx, redisServer)

}
