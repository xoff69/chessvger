package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.dao.CommonDao;
import com.xoff.chessvger.backoffice.dao.ContractDao;
import com.xoff.chessvger.backoffice.dao.DatabaseDao;
import com.xoff.chessvger.backoffice.dao.FeatureFlagDao;
import com.xoff.chessvger.backoffice.dao.PlayerDao;
import com.xoff.chessvger.backoffice.dao.UserDao;
import java.sql.Connection;


public class RunInitSystem  implements Runnable {


  @Override
  public void run() {
    try (Connection connection=CommonDao.getConnection()){
      if (CommonDao.createSchemaIfNotExists(connection, CommonDao.COMMON_SCHEMA)){
        CommonDao.createTable(connection, PlayerDao.TABLE_PLAYER);
      }

      if (CommonDao.createSchemaIfNotExists(connection, CommonDao.ADMIN_SCHEMA)){
        CommonDao.createTable(connection, UserDao.TABLE_USER);
        UserDao.createUser(connection,"admin","admin name","admin",true);
        CommonDao.createTable(connection, DatabaseDao.TABLE_DATABASE, CommonDao.ADMIN_SCHEMA);
        DatabaseDao.insertDefaultDatabase(connection, CommonDao.ADMIN_SCHEMA);
        CommonDao.createTable(connection, ContractDao.TABLE_CONTRACT);
        ContractDao.insertDefautContract(connection);
        CommonDao.createTable(connection, FeatureFlagDao.TABLE_FEATURE_FLAG);

        // toutes les tables game of a player ... mais dont le nom est dynamique, a reflechir

      }

    } catch (Exception e) {
      System.out.println("Error RunInitSystem");
      throw new RuntimeException(e);
    }
  }
}
