package com.xoff.chessvger.backoffice.dao;

import java.sql.Connection;

public class DatabaseDao {

  public static final String DEFAULT_DATABASE_NAME = "Main 2025";

  public static final String TABLE_DATABASE=   "CREATE TABLE %s.database (\n" +
     " id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
      "   name VARCHAR(255) NOT NULL,\n" +
      "description TEXT,\n" +
      "date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
      "date_update TIMESTAMP,\n" +
      ");";

  /**
   * create dababase en subtable: games, ...
   * @param schemaName
   */
  public static void createDatabase(String schemaName,String name) {
    try (Connection connection= CommonDao.getConnection()){

      String sql = String.format(TABLE_DATABASE, schemaName);

      CommonDao.createTable(connection,sql);
      // table des games de la db
      // table des stats
      // table game of player
      // table position + subtable
      // table material+ subtable
    } catch (Exception e) {
      System.out.println("Error RunInitTenant");
      throw new RuntimeException(e);
    }
  }

  public static void insertDefaultDatabase(Connection connection, String adminSchema) {
    throw new UnsupportedOperationException("Not yet implemented");
    // create dabase avec un certain nom DEFAULT_DATABASE_NAME
  }

  public static void duplicate(String srcSchema, String srcDatabaseName, String destSchemaName, String desttDatabaseName) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
