package com.xoff.chessvger.backoffice.dao;

import java.sql.Connection;

public class DatabaseDao {
  public static final String TABLE_DATABASE=   "CREATE TABLE %s.database (\n" +
     " id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
      "   name VARCHAR(255) NOT NULL,\n" +
      "description TEXT,\n" +
      "date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
      "date_update TIMESTAMP,\n" +
      ");";

  public static void createDatabase(String schemaName) {
    try (Connection connection= CommonDao.getConnection()){

      String sql = String.format(TABLE_DATABASE, schemaName);

      CommonDao.createTable(connection,sql);
    } catch (Exception e) {
      System.out.println("Error RunInitTenant");
      throw new RuntimeException(e);
    }
  }

  public static void insertDefaultDatabase(Connection connection, String adminSchema) {
  }
}
