package com.xoff.chessvger.backoffice.environnement;

import com.xoff.chessvger.dao.CommonDao;
import com.xoff.chessvger.dao.TenantDao;
import com.xoff.chessvger.dao.UserDao;
import com.xoff.chessvger.model.UserTenant;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
@Slf4j
public class RunInitTenant implements Runnable {

    private final UserTenant userTenant;

    public RunInitTenant(UserTenant userTenant) {
        this.userTenant = userTenant;
    }

    @Override
    public void run() {

        try (Connection connection = CommonDao.getConnection()) {
            int tenantId = TenantDao.createTenant(connection, userTenant.getTenantName());
            UserDao.createUser(connection, userTenant.getLogin(), userTenant.getTenantName(),
                    userTenant.getPassword(), false, tenantId);

            // TODO ContractDao.linkUserToContract(userTenant, ContractDao.getDefaultContract());
            log.info("Tenant created");
            TenantDao.createTenantEnvironnement(userTenant.getTenantName());

// tenantDao initialise une nouvelle bd pg, et en plus on cree un schema dans cette nouvelle bd
            // et on fait une copie
            // TODO  TenantDao.duplicate(CommonDao.COMMON_SCHEMA, TenantDao.DEFAULT_DATABASE_NAME, schemaName,TenantDao.DEFAULT_DATABASE_NAME);


        } catch (Exception e) {
            log.error("Error RunInitTenant",e);
        }
    }
}
