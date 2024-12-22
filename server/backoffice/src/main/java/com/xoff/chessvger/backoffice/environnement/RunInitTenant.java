package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.backoffice.util.CommonDao;
import com.xoff.chessvger.backoffice.util.DatabaseManager;
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
      DatabaseManager.createUser(connection,userTenant.getLogin(),userTenant.getName(),userTenant.getPassword(),false); // admin

      DatabaseManager.linkUserToContract(userTenant,DatabaseManager.getDefaultContract());
      String schemaName = String.format(DatabaseManager.SCHEMA_TENANT_PATTERN, String.valueOf(userTenant.getId()));

      if (DatabaseManager.createSchemaIfNotExists(connection,schemaName)){
        DatabaseManager.createDatabase(schemaName);
        // TODO : lui copier la database par defaut liee au contrat
      }


    } catch (Exception e) {
      System.out.println("Error RunInitTenant");
      throw new RuntimeException(e);
    }
  }
}
