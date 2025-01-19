package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.dao.CommonDao;
import com.xoff.chessvger.backoffice.dao.ContractDao;
import com.xoff.chessvger.backoffice.dao.TenantDao;
import com.xoff.chessvger.backoffice.dao.UserDao;
import com.xoff.chessvger.backoffice.util.FileUtils;
import java.sql.Connection;


public class RunInitSystem  implements Runnable {


  @Override
  public void run() {

    try (Connection connection=CommonDao.getConnection()){
      // schema common: user, contract, player, feature flag
      if (CommonDao.createSchemaIfNotExists(connection, CommonDao.COMMON_SCHEMA)){

        String queryPlayerTable= FileUtils.read("query/player_createtable.sql");
        System.out.println("queryPlayerTable "+queryPlayerTable);
        CommonDao.executeQuery(connection, queryPlayerTable);

        String queryUserTable= FileUtils.read("query/user_createtable.sql");
        System.out.println("queryUserTable "+queryUserTable);
        CommonDao.executeQuery(connection, queryUserTable);
int tenantId=TenantDao.createTenant(connection,"admin");
        UserDao.createUser(connection,"admin","admin name","admin",true,tenantId);

        String queryContractTable= FileUtils.read("query/contract_createtable.sql");
        System.out.println("queryContractTable "+queryContractTable);
        CommonDao.executeQuery(connection, queryContractTable);

        ContractDao.insertDefautContract(connection);

        String queryFeatureFlagTable= FileUtils.read("query/featureflag_createtable.sql");
        System.out.println("queryFeatureFlagTable "+queryFeatureFlagTable);
        CommonDao.executeQuery(connection, queryFeatureFlagTable);

      }
      // database pg for ad,in
      TenantDao.createTenantEnvironnement("admin");

    } catch (Exception e) {
      System.out.println("Error RunInitSystem");
      throw new RuntimeException(e);
    }
  }
}
