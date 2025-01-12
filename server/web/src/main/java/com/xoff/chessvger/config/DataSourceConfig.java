package com.xoff.chessvger.config;

import com.xoff.chessvger.repository.DynamicRoutingDataSource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Configuration
public class DataSourceConfig {

  @Bean
  public DataSource dynamicDataSource() {
    DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
    DataSource dataSource1 = createDataSource(
        "jdbc:postgresql://db_chessvger/chessvger_admin_database",
        "chessvger",
        "chessvger");

    // Associer chaque source de données à une clé
    Map<Object, Object> dataSources = new HashMap<>();
    dataSources.put("DEFAULT", dataSource1);

    // Configurer les sources de données
    routingDataSource.setTargetDataSources(dataSources);

    // Définir une source de données par défaut
    routingDataSource.setDefaultTargetDataSource(dataSource1);

    return routingDataSource;
  }

  private DataSource createDataSource(String url, String username, String password) {
    // Exemple avec HikariCP
    com.zaxxer.hikari.HikariDataSource dataSource = new com.zaxxer.hikari.HikariDataSource();
    dataSource.setJdbcUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }
}