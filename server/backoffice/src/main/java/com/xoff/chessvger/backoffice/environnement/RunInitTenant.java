package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.dao.CommonDao;
import com.xoff.chessvger.backoffice.dao.ContractDao;
import com.xoff.chessvger.backoffice.dao.TenantDao;
import com.xoff.chessvger.backoffice.dao.UserDao;
import com.xoff.chessvger.common.UserTenant;
import java.sql.Connection;

public class RunInitTenant implements Runnable {

  private UserTenant userTenant;
  public RunInitTenant(UserTenant userTenant) {
    this.userTenant = userTenant;
  }
  @Override
  public void run() {

    try (Connection connection= CommonDao.getConnection()){
      UserDao.createUser(connection,userTenant.getLogin(),userTenant.getName(),userTenant.getPassword(),false); // admin

      // TODO ContractDao.linkUserToContract(userTenant, ContractDao.getDefaultContract());
      String schemaName = String.format(CommonDao.SCHEMA_TENANT_PATTERN, String.valueOf(userTenant.getId()));

      TenantDao.createTenantEnvironnement(userTenant.getLogin());

// tenantDao initialise une nouvelle bd pg, et en plus on cree un schema dans cette nouvelle bd
      // et on fait une copie
     // TODO  TenantDao.duplicate(CommonDao.COMMON_SCHEMA, TenantDao.DEFAULT_DATABASE_NAME, schemaName,TenantDao.DEFAULT_DATABASE_NAME);



    } catch (Exception e) {
      System.out.println("Error RunInitTenant");
      throw new RuntimeException(e);
    }
  }
}
