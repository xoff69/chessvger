package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.dao.CommonDao;
import com.xoff.chessvger.backoffice.dao.ContractDao;
import com.xoff.chessvger.backoffice.dao.DatabaseDao;
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

      ContractDao.linkUserToContract(userTenant, ContractDao.getDefaultContract());
      String schemaName = String.format(CommonDao.SCHEMA_TENANT_PATTERN, String.valueOf(userTenant.getId()));

      if (CommonDao.createSchemaIfNotExists(connection,schemaName)){
        DatabaseDao.createDatabase(schemaName);
        // TODO : lui copier la database par defaut liee au contrat
      }


    } catch (Exception e) {
      System.out.println("Error RunInitTenant");
      throw new RuntimeException(e);
    }
  }
}
