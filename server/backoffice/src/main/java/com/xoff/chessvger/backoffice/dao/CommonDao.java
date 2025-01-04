package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

// TODO gestion des exceptions
// TODO gestion des databases: comment on switche
// TODO gestion des exceptions
// TODO gestion des databases: comment on switche
public class CommonDao {
  public static final String COMMON_SCHEMA="common";
  public static final String SCHEMA_TENANT_PATTERN="tenant_%s";

  private static final String CHECK_SCHEMA_EXISTS_SQL =
      "SELECT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = ?)";
  private static final String CREATE_SCHEMA_SQL =
      "CREATE SCHEMA IF NOT EXISTS %s";


  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("connexion DB :" + e);
    }
    // System.out.println("connexion DB :" + "jdbc:postgresql://" + Main.getDBHost() + "/chessvger");


  }

private static Map<String,HikariDataSource> mapDatasource = new HashMap();

  private CommonDao() {
  }

  public static Connection getConnection() throws SQLException {
    return getConnection("chessvger");
  }
  public static Connection getConnection(String name) throws SQLException {
    if (!mapDatasource.containsKey(name)) {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl("jdbc:postgresql://" + Main.getDBHost() + "/"+name);
      config.setUsername("chessvger");
      config.setPassword("chessvger");
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
      config.setAutoCommit(true);

      mapDatasource.put(name,new HikariDataSource(config));
    }
    return mapDatasource.get(name).getConnection();
  }
  public static boolean createSchemaIfNotExists(Connection connection, String schemaName)
      throws Exception {
    if (!schemaExists(connection, schemaName)) {
      createSchema(connection, schemaName);
    }
    return true;
  }

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

  private static void createSchema(Connection connection, String schemaName) throws Exception {
    String sql = String.format(CREATE_SCHEMA_SQL, schemaName);
    try (Statement stmt = connection.createStatement()) {
      stmt.execute(sql);
      System.out.println("Schema created: " + schemaName);
    }
  }

  // Méthode pour créer une table
  public static void executeQuery(Connection connection, String query)  {

    try (Statement stmt = connection.createStatement()) {
      stmt.execute(query);
      System.out.println("Query executed: " + query);
    } catch (Exception e) {
      System.out.println("Error executeQuery "+query+ " "+e.getMessage());

    }
  }
  public static void createDatabasePg(Connection connection, String databaseName) throws Exception {


    try (Statement statement = connection.createStatement()) {

      // Requête SQL pour créer une nouvelle base de données
      String sql = "CREATE DATABASE chessvger_" + databaseName+"_database";

      // Exécution de la requête
      statement.executeUpdate(sql);
      System.out.println("Base de données créée avec succès : " + databaseName);

    } catch (SQLException e) {
      System.out.println("Erreur lors de la création de la base de données : " + e.getMessage());
    }
  }

}