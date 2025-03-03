package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.*;
import com.xoff.chessvger.ui.web.controller.tools.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TenantServiceImpl implements TenantService {

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserService userService;


    public Optional<TenantEntity> getTenant(long  tenantId){

        log.info("getTenant, userId: " + tenantId);
        dynamicDataSourceService.addNewDataSource("common",
                "jdbc:postgresql://db_chessvger/chessvger",
                "chessvger",
                "chessvger","common");

        // Changer la source de données actuelle pour "newDb"
        DataSourceContextHolder.setDataSource("common");
        return tenantRepository.findById(tenantId);
    }
    public Optional<TenantEntity> getByUserId(long userId){
        log.info("getByUserId, userId: " + userId);
        dynamicDataSourceService.addNewDataSource("common",
                "jdbc:postgresql://db_chessvger/chessvger",
                "chessvger",
                "chessvger","common");

        // Changer la source de données actuelle pour "newDb"
        DataSourceContextHolder.setDataSource("common");
        // TODO appeler redis pour avoir le tenantId?
        // on va mettre en place un cache userID -? tenant
        UserDTO user=userService.getById(userId);
        return tenantRepository.findById(user.getTenantId());
    }

}
