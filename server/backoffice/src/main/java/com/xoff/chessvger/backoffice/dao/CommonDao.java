package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO gestion des exceptions
public class CommonDao {
  public static final String COMMON_SCHEMA="common";
  public static final String ADMIN_SCHEMA="admin";
  public static final String SCHEMA_TENANT_PATTERN="tenant_%s";
  private static final HikariConfig config = new HikariConfig();
  private static final HikariDataSource ds;
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

    config.setJdbcUrl("jdbc:postgresql://" + Main.getDBHost() + "/chessvger");
    config.setUsername("chessvger");
    config.setPassword("chessvger");
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.setAutoCommit(true);
    ds = new HikariDataSource(config);
  }

  private CommonDao() {
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
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
  public static void createTable(Connection connection, String query) throws Exception {

    try (Statement stmt = connection.createStatement()) {
      stmt.execute(query);
      System.out.println("Table created: " + query);
    } catch (Exception e) {
      System.out.println("Error RunInitTenant");
      throw new RuntimeException(e);
    }
  }

  public static void createTable(Connection connection, String query, String schemaName) throws Exception {
    String sql = String.format(DatabaseDao.TABLE_DATABASE, schemaName);
    createTable(connection,sql);

  }
}
