package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.dao.CommonDao;
import com.xoff.chessvger.backoffice.dao.ContractDao;
import com.xoff.chessvger.backoffice.dao.TenantDao;
import com.xoff.chessvger.backoffice.dao.UserDao;
import java.sql.Connection;


public class RunInitSystem implements Runnable {


  @Override
  public void run() {

    try (Connection connection = CommonDao.getConnection()) {
      // schema common: user, contract, player, feature flag
      if (CommonDao.createSchemaIfNotExists(connection, CommonDao.COMMON_SCHEMA)) {


        CommonDao.executeSqlFromFile(connection, "query/player_createtable.sql");
        CommonDao.executeSqlFromFile(connection, "query/tenant_createtable.sql");
        CommonDao.executeSqlFromFile(connection, "query/user_createtable.sql");

        int tenantId = TenantDao.createTenant(connection, "admin");
        UserDao.createUser(connection, "admin", "admin name", "admin", true, tenantId);


        CommonDao.executeSqlFromFile(connection, "query/contract_createtable.sql");
        ContractDao.insertDefautContract(connection);

        CommonDao.executeSqlFromFile(connection, "query/featureflag_createtable.sql");
      }
      // database pg for ad,in
      TenantDao.createTenantEnvironnement("admin");

      // creer la database admin
      CommonDao.executeQuery(connection,
          "insert into common.database(name,description) values('main','main database')");

    } catch (Exception e) {
      System.out.println("Error RunInitSystem");
      throw new RuntimeException(e);
    }
  }
}
