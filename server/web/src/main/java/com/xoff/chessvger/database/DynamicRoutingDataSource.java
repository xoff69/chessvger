package com.xoff.chessvger.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final Map dataSourceMap = new HashMap();

    // Méthode pour ajouter une datasource à la volée
    public void addDataSource(String key, DataSource dataSource) {

        log.info("addDataSource " + key + " dataSource: ");

        if (!dataSourceMap.containsKey(key)) {
            dataSourceMap.put(key, dataSource);
            setTargetDataSources(dataSourceMap);
            afterPropertiesSet();
        } else {
            log.info("DataSource with key " + key + " already exists.");
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

}
