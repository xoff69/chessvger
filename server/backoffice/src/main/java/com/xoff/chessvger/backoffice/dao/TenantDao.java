package com.xoff.chessvger.backoffice.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TenantDao {
  public static void createTenantEnvironnement(String name) {
    // cree une database pg
    try (Connection connection = CommonDao.getConnection()) {

      CommonDao.createDatabasePg(connection, name);
      // TODO creer un schema common
      CommonDao.createTable(connection, DatabaseDao.TABLE_DATABASE, CommonDao.COMMON_SCHEMA);
      // DatabaseDao.insertDefaultChessvgerDatabase(connection, CommonDao.COMMON_SCHEMA);

      // cree une database pour la liste des bd dans le schema common
      String schemaName="dbcv_main";
      if (CommonDao.createSchemaIfNotExists(connection,schemaName)){
        DatabaseDao.createChessvgerDatabase(schemaName,DatabaseDao.DEFAULT_DATABASE_NAME);
       }
    } catch (SQLException e) {
      System.out.println("Erreur lors de la création de la base de données : " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
