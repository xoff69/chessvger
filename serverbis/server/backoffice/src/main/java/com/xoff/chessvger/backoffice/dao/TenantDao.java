package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.util.FileUtils;
import java.sql.Connection;
import java.sql.SQLException;
public class TenantDao {
  public static void createTenantEnvironnement(String tenantName) {

    try (Connection connection = CommonDao.getConnection()) {

      CommonDao.createDatabasePg(connection, tenantName);
      String name="chessvger_"+tenantName+"_database";
      try (Connection connectionTenant = CommonDao.getConnection(name)) {
        // schema common
        CommonDao.createSchemaIfNotExists(connectionTenant, CommonDao.COMMON_SCHEMA);
        // puis un schema par bd
        String queryDatabaseTable= FileUtils.read("query/databasetable.sql");
        System.out.println("queryDatabaseTable "+queryDatabaseTable);
        CommonDao.executeQuery(connectionTenant, queryDatabaseTable);
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

    String queryGameTable= FileUtils.read("query/gametable.sql");

    System.out.println("queryGameTable "+queryGameTable);
    String sql = String.format(queryGameTable, schemaNameString);
    System.out.println("sql "+sql);
    CommonDao.executeQuery(connection, sql);

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
