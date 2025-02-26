package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DynamicDataSourceService;
import com.xoff.chessvger.repository.TenantEntity;
import com.xoff.chessvger.ui.JwtUtil;
import com.xoff.chessvger.ui.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DatabaseHelperServiceImpl implements  DatabaseHelperService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserService userService;

    // TODO mettre un tenantDto
    private TenantEntity getFromToken(String token){
        log.info("getFromToken, token: " + token);
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        UserDTO user = userService.getUserByUsername(username);
        return tenantService.getByUserId(user.getId());

    }

    public void setDatasource(String token, String schema){
        // jdbc:postgresql://db_chessvger/chessvger_admin_database?currentSchema=main
        log.info("setDatasource, token: {}, schema: {}", token, schema);
        TenantEntity tenantEntity = getFromToken(token);
        String name=tenantEntity.getName();
        String key=name+"_DB_"+schema;
        dynamicDataSourceService.addNewDataSource(key,
                "jdbc:postgresql://db_chessvger/chessvger_"+name+"_database",
                "chessvger",
                "chessvger",schema);

        // Changer la source de données actuelle pour "newDb"
        DataSourceContextHolder.setDataSource(key);
    }

}
