package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.dao.CommonDao;
import com.xoff.chessvger.backoffice.dao.ContractDao;
import com.xoff.chessvger.backoffice.dao.DatabaseDao;
import com.xoff.chessvger.backoffice.dao.FeatureFlagDao;
import com.xoff.chessvger.backoffice.dao.PlayerDao;
import com.xoff.chessvger.backoffice.dao.TenantDao;
import com.xoff.chessvger.backoffice.dao.UserDao;
import java.sql.Connection;


public class RunInitSystem  implements Runnable {


  @Override
  public void run() {
    try (Connection connection=CommonDao.getConnection()){
      // creer le schema common pour les players dans le bd pg  chessvger
      if (CommonDao.createSchemaIfNotExists(connection, CommonDao.COMMON_SCHEMA)){
        CommonDao.createTable(connection, PlayerDao.TABLE_PLAYER);

        CommonDao.createTable(connection, UserDao.TABLE_USER);
        UserDao.createUser(connection,"admin","admin name","admin",true);

        CommonDao.createTable(connection, ContractDao.TABLE_CONTRACT);
        ContractDao.insertDefautContract(connection);
        CommonDao.createTable(connection, FeatureFlagDao.TABLE_FEATURE_FLAG);
      }
      // on cree une database pg pour le tenant admin
      TenantDao.createTenantEnvironnement("admin");

    } catch (Exception e) {
      System.out.println("Error RunInitSystem");
      throw new RuntimeException(e);
    }
  }
}
