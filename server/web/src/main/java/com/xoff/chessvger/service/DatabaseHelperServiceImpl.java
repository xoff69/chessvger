package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DynamicDataSourceService;
import com.xoff.chessvger.repository.TenantEntity;
import com.xoff.chessvger.ui.JwtUtil;
import com.xoff.chessvger.ui.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<TenantEntity> getFromToken(String token){
        log.info("getFromToken, token: " + token);
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        UserDTO user = userService.getUserByUsername(username);
        return tenantService.getByUserId(user.getId());

    }

    public void setDatasource(String token, String schema){
        // jdbc:postgresql://db_chessvger/chessvger_admin_database?currentSchema=main
        log.info("setDatasource, token: {}, schema: {}", token, schema);
        Optional<TenantEntity> opt = getFromToken(token);
        if (opt.isPresent()) {
            TenantEntity tenantEntity = opt.get();
            String name = tenantEntity.getName();
            String key = name + "_DB_" + schema;
            log.info("setDatasource, key: {} ", key);
            dynamicDataSourceService.addNewDataSource(key,
                    "jdbc:postgresql://db_chessvger/chessvger_" + name + "_database",
                    "chessvger",
                    "chessvger", schema);

            DataSourceContextHolder.setDataSource(key);
        }
        else {
            log.info("setDatasource TENANT not found, token: {}, schema: {}", token, schema);
        }
    }

}
