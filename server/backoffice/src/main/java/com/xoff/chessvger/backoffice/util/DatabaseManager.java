package com.xoff.chessvger.backoffice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
// pgadmin a lancer aussi

public class DatabaseManager {

  // Centralisation des requêtes SQL
  private static final String CHECK_SCHEMA_EXISTS_SQL =
      "SELECT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = ?)";
  private static final String CREATE_SCHEMA_SQL =
      "CREATE SCHEMA IF NOT EXISTS %s"; // Utilisation de substitution pour le nom du schéma
  private static final String CREATE_TABLE_SQL =
      "CREATE TABLE IF NOT EXISTS %s.%s (" +
          "id SERIAL PRIMARY KEY, " +
          "login VARCHAR(255) NOT NULL, " +
          "description TEXT, " +
          "password VARCHAR(255) NOT NULL, " +
          "creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
          "last_connection TIMESTAMP, " +
          "profil BOOLEAN)";
  private static final String INSERT_ROW_SQL =
      "INSERT INTO %s.%s (login, description, password, last_connection, profil) " +
          "VALUES (?, ?, ?, ?, ?)";

  // Paramètres de connexion à la base
  private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database";
  private static final String DB_USER = "your_username";
  private static final String DB_PASSWORD = "your_password";

  public static void main(String[] args) {
    String schemaName = "users";
    String tableName = "user_data";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      if (!schemaExists(connection, schemaName)) {
        createSchema(connection, schemaName);
      }
      createTable(connection, schemaName, tableName);
      insertRow(connection, schemaName, tableName, "admin", "Administrator", "securepassword", null, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Méthode pour vérifier si un schéma existe
  private static boolean schemaExists(Connection connection, String schemaName) throws Exception {
    try (PreparedStatement stmt = connection.prepareStatement(CHECK_SCHEMA_EXISTS_SQL)) {
      stmt.setString(1, schemaName);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getBoolean(1);
        }
      }
    }
    return false;
  }

  // Méthode pour créer un schéma
  private static void createSchema(Connection connection, String schemaName) throws Exception {
    String sql = String.format(CREATE_SCHEMA_SQL, schemaName);
    try (Statement stmt = connection.createStatement()) {
      stmt.execute(sql);
      System.out.println("Schema created: " + schemaName);
    }
  }

  // Méthode pour créer une table
  private static void createTable(Connection connection, String schemaName, String tableName) throws Exception {
    String sql = String.format(CREATE_TABLE_SQL, schemaName, tableName);
    try (Statement stmt = connection.createStatement()) {
      stmt.execute(sql);
      System.out.println("Table created: " + schemaName + "." + tableName);
    }
  }

  // Méthode pour insérer une ligne dans la table
  private static void insertRow(Connection connection, String schemaName, String tableName,
                                String login, String description, String password,
                                java.sql.Timestamp lastConnection, boolean profil) throws Exception {
    String sql = String.format(INSERT_ROW_SQL, schemaName, tableName);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, login);
      stmt.setString(2, description);
      stmt.setString(3, password);
      stmt.setTimestamp(4, lastConnection); // Peut être null
      stmt.setBoolean(5, profil);
      stmt.executeUpdate();
      System.out.println("Row inserted: " + login);
    }
  }
}