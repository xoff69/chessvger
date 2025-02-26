package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.repository.DatabaseRepository;
import java.util.List;

import com.xoff.chessvger.repository.DynamicDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseServiceImpl implements IDatabaseService {

  @Autowired
  private DynamicDataSourceService dynamicDataSourceService;
  @Autowired
  private DatabaseRepository databaseRepository;
  public Long count() {

    dynamicDataSourceService.addNewDataSource("newDb",
            "jdbc:postgresql://db_chessvger/chessvger_admin_database",
            "chessvger",
            "chessvger","main");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("newDb");
    return databaseRepository.count();
  }
  public List<DatabaseEntity> findAll(){

    dynamicDataSourceService.addNewDataSource("newDb",
            "jdbc:postgresql://db_chessvger/chessvger_admin_database",
            "chessvger",
            "chessvger","main");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("newDb");

    org.springframework.data.domain.Page<DatabaseEntity> page=databaseRepository.findAll(
        org.springframework.data.domain.Pageable.ofSize(5));
    return  page.stream().toList();
  }

}
