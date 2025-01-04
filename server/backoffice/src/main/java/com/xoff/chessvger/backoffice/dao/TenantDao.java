package com.xoff.chessvger.backoffice.dao;

import java.sql.Connection;
import java.sql.SQLException;
public class TenantDao {
  public static void createTenantEnvironnement(String tenantName) {
    // cree une database pg
    try (Connection connection = CommonDao.getConnection()) {

      CommonDao.createDatabasePg(connection, tenantName);
      try (Connection connectionTenant = CommonDao.getConnection(tenantName)) {
        // schema common
        CommonDao.createSchemaIfNotExists(connectionTenant, CommonDao.COMMON_SCHEMA);
        // puis un schema par bd
        CommonDao.executeQuery(connectionTenant, TABLE_DATABASE);
        String schemaName="main_"+tenantName;
        CommonDao.createSchemaIfNotExists(connectionTenant, schemaName);
        createChessvgerDatabase(connectionTenant,schemaName);

      }
    } catch (SQLException e) {
      System.out.println(tenantName+" ::Error createTenantEnvironnement : " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  /**
   * create dababase en subtable: games, ...
   *
   */
  private static void createChessvgerDatabase(Connection connection,String schemaNameString) {


    //CommonDao.executeQuery(connection,sql);
    // table des games de la db
    // table des stats
    // table game of player
    // table position + subtable
    // table material+ subtable
    // filiation
    // favorite
    // filter
    // history
    // statisque + meilleurs joueurs stat

  }

  public static final String DEFAULT_DATABASE_NAME = "Main 2025";

  public static final String TABLE_DATABASE=   "CREATE TABLE %s.database (\n" +
      " id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
      "   name VARCHAR(255) NOT NULL,\n" +
      "description TEXT,\n" +
      "date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
      "date_update TIMESTAMP,\n" +
      ");";



// utiliser db link pour faire cela
  /*
CREATE EXTENSION dblink; // base cible
INSERT INTO target_table (col1, col2, col3)
SELECT col1, col2, col3
FROM dblink('host=host_source dbname=database_source user=user_source password=your_password',
            'SELECT col1, col2, col3 FROM source_table')
            AS source_table_alias(col1 TYPE, col2 TYPE, col3 TYPE);
  */

  public static void duplicate(String srcSchemaName, String srcDatabaseName, String destSchemaName, String desttDatabaseName) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
