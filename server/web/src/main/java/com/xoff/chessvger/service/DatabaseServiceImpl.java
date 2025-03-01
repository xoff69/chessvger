package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.repository.DatabaseRepository;

import java.sql.SQLException;
import java.util.List;

import com.xoff.chessvger.repository.DynamicDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseServiceImpl implements IDatabaseService {

  @Autowired
  private DynamicDataSourceService dynamicDataSourceService;
  @Autowired
  private DatabaseRepository databaseRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;
// TODO A generaliser
public List<String> listTables() {
  String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = '"+getCurrentSchema()+"'";
  return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("table_name"));
}
  public String getCurrentDatabase() {
    return jdbcTemplate.queryForObject("SELECT current_database()", String.class);
  }
  public String getCurrentSchema() {
    return jdbcTemplate.queryForObject("SELECT current_schema()", String.class);
  }

  public Long count() {

    return databaseRepository.count();
  }
  public List<DatabaseEntity> findAll() {

    try {
      org.springframework.data.domain.Page<DatabaseEntity> page = databaseRepository.findAll(
              org.springframework.data.domain.Pageable.ofSize(5));
      return page.stream().toList();
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getStackTrace().toString());
      log.info("CURRENT DATABASE:"+getCurrentDatabase());
      log.info("CURRENT SCHEMA:"+getCurrentSchema());
      log.info("Ctables {}:",listTables());
      throw new RuntimeException(e);

    }
  }

}
