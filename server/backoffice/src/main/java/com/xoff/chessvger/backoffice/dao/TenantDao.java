package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.util.FileUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class TenantDao {

  private static final String INSERT_TENANT="INSERT INTO common.tenants (name, date_created, date_updated)\n" +
      "VALUES\n" + "    (?, CURRENT_DATE, CURRENT_TIMESTAMP) returning tenant_id";
  public static int createTenant(Connection connection,
                                String name) throws Exception {

    try (PreparedStatement stmt = connection.prepareStatement(INSERT_TENANT)) {
      stmt.setString(1, name);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int generatedId = rs.getInt("tenant_id"); // Adaptez ici au nom du champ
          System.out.println("Tenant ID généré : " + generatedId);
          return generatedId;
        }
      }
    }
    catch (Exception e){
      System.out.println("Error createTenant: " + name+" "+e.getMessage());
    }
    return 0;
  }
  public static void createTenantEnvironnement(String tenantName) {

    try (Connection connection = CommonDao.getConnection()) {
      String sqlDatabaseName="chessvger_"+tenantName+"_database";
      CommonDao.createDatabasePg(connection, sqlDatabaseName);

      try (Connection connectionTenant = CommonDao.getConnection(sqlDatabaseName)) {
        // schema common
        CommonDao.createSchemaIfNotExists(connectionTenant, CommonDao.COMMON_SCHEMA);
        // puis un schema par bd
        CommonDao.executeSqlFromFile(connectionTenant,"query/database_createtable.sql");

        String schemaName="main";
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

    String queryGameTable= FileUtils.read("query/game_createtable.sql");

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
