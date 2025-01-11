package com.xoff.chessvger.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
public class DynamicDataSourceService {

  private final DynamicRoutingDataSource dynamicRoutingDataSource;

  public DynamicDataSourceService(DynamicRoutingDataSource dynamicRoutingDataSource) {
    this.dynamicRoutingDataSource = dynamicRoutingDataSource;
  }

  // Ajouter une nouvelle DataSource à la volée
  public void addNewDataSource(String key, String jdbcUrl, String username, String password,String schema) {
    DataSource newDataSource = createDataSource(jdbcUrl, username, password,schema);
    dynamicRoutingDataSource.addDataSource(key, newDataSource);
  }

  // Créer une datasource
  private DataSource createDataSource(String url, String username, String password,String schema) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(url);
dataSource.setUsername(username);
dataSource.setPassword(password);
 dataSource.setConnectionInitSql("SET search_path TO "+schema);

    return dataSource;
  }
}
