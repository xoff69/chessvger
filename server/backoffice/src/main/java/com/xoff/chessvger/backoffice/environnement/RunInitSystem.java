package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.util.CommonDao;
import com.xoff.chessvger.backoffice.util.DatabaseManager;
import java.sql.Connection;


public class RunInitSystem  implements Runnable {


  @Override
  public void run() {
    try (Connection connection=CommonDao.getConnection()){
      if (DatabaseManager.createSchemaIfNotExists(connection,DatabaseManager.COMMON_SCHEMA)){
        DatabaseManager.createTable(connection,DatabaseManager.TABLE_PLAYER);
      }

      if (DatabaseManager.createSchemaIfNotExists(connection,DatabaseManager.ADMIN_SCHEMA)){
        DatabaseManager.createTable(connection,DatabaseManager.TABLE_USER);
        DatabaseManager.createUser(connection,"admin","admin name","admin",true);

        DatabaseManager.createTable(connection,DatabaseManager.TABLE_CONTRACT);
        DatabaseManager.insertDefautContract(connection);
        DatabaseManager.createTable(connection,DatabaseManager.FEATURE_FLAG);

      }

    } catch (Exception e) {
      System.out.println("Error RunInitSystem");
      throw new RuntimeException(e);
    }
  }
}
