package com.xoff.chessvger.repository;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

  private Map dataSourceMap = new HashMap();

  // Méthode pour ajouter une datasource à la volée
  public void addDataSource(String key, DataSource dataSource) {
    if (!dataSourceMap.containsKey(key)) {
      dataSourceMap.put(key, dataSource);
      setTargetDataSources(dataSourceMap);// Met à jour les sources de données disponibles
      afterPropertiesSet();// Rafraîchit les propriétés
    } else {
      // La datasource existe déjà, vous pouvez choisir de lever une exception ou faire un log
      System.out.println("DataSource with key " + key + " already exists.");
    }
  }

  @Override
  protected Object determineCurrentLookupKey() {
    return DataSourceContextHolder.getDataSource();
  }

}
