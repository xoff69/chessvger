package com.xoff.chessvger.service;

import com.xoff.chessvger.database.DataSourceContextHolder;
import com.xoff.chessvger.database.DynamicDataSourceService;
import com.xoff.chessvger.model.TenantEntity;
import com.xoff.chessvger.model.UserEntity;
import com.xoff.chessvger.ui.web.controller.tools.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.xoff.chessvger.ui.web.controller.tools.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Autowired
  private DynamicDataSourceService dynamicDataSourceService;
  @Autowired
  private JdbcTemplate jdbcTemplate;


  public UserDTO getById(long id){
    // TODO
    dynamicDataSourceService.addNewDataSource("common",
            "jdbc:postgresql://db_chessvger/chessvger",
            "chessvger",
            "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    Optional<UserEntity> userEntity = findUserById(id);
    if (!userEntity.isPresent()){
      throw  new RuntimeException("user not found");
    }
    return mapToDTO(userEntity.get());
  }
  public UserDTO getUserByUsername(String username) {
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    return mapToDTO(findByLogin(username).orElse(null));
  }

  public Long count() {
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    return count();
  }
  public List<UserDTO> findAll(Pageable pageable){
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    String sql = "SELECT id, login, description, date_created, date_updated, profil, tenant_id " +
            "FROM common.users LIMIT ? OFFSET ?";
    long total = count();
    List<UserEntity> userEntities = jdbcTemplate.query(sql, new Object[]{pageable.getPageSize(), pageable.getOffset()},
            new UserRowMapper());

    return userEntities.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());

  }
  public UserDTO findByLoginAndPassword(String login, String password){
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    return mapToDTO(repofindByLoginAndPassword(login, password).orElse(null));
  }

  private UserDTO mapToDTO(UserEntity userEntity) {
    return UserMapper.toDto(userEntity);
  }
  private Optional<UserEntity> repofindByLoginAndPassword(String login, String password) {
    String sql = "SELECT id, login, description, password, date_created, date_updated, profil, tenant_id " +
            "FROM common.users WHERE login = ? AND password = ?";

    return jdbcTemplate.query(sql, new Object[]{login, password}, new UserRowMapper())
            .stream()
            .findFirst();
  }
  private Optional<UserEntity> findUserById(long id) {
    String sql = "SELECT id, login, description, password, date_created, date_updated, profil, tenant_id " +
            "FROM common.users WHERE id = ?";

    return jdbcTemplate.query(sql, new Object[]{id}, new UserRowMapper())
            .stream()
            .findFirst();
  }


  public List<UserEntity> list() {
    String sql = "SELECT id, login, description, password, date_created, date_updated, profil, tenant_id " +
            "FROM common.users";

    return jdbcTemplate.query(sql, new UserRowMapper());
  }

  // ✅ RowMapper pour mapper un User
    class UserRowMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
      Optional<TenantEntity> tenantEntity=getTenant(rs.getLong("tenant_id"));
      return new UserEntity(
              rs.getLong("id"),
              rs.getString("login"),
              rs.getString("description"),
              rs.getString("password"),
              rs.getTimestamp("date_created").toLocalDateTime(),
              rs.getTimestamp("date_updated").toLocalDateTime(),
              rs.getBoolean("profil"),
              tenantEntity.orElse(null)
      );
    }
  }
  public Optional<UserEntity> findByLogin(String login) {
    String sql = "SELECT id, login, description, password, date_created, date_updated, profil, tenant_id " +
            "FROM common.users WHERE login = ?";

    return jdbcTemplate.query(sql, new Object[]{login}, new UserRowMapper())
            .stream()
            .findFirst();
  }



  public Optional<TenantEntity> getTenant(long  tenantId){

    log.info("getTenant, userId: " + tenantId);
    dynamicDataSourceService.addNewDataSource("common",
            "jdbc:postgresql://db_chessvger/chessvger",
            "chessvger",
            "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    return findById(tenantId);
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
    UserDTO user=getById(userId);
    return findById(user.getTenantId());
  }
  private Optional<TenantEntity> findById(long tenantId) {
    String sql = "SELECT tenant_id, name, date_created, date_updated FROM common.tenants WHERE tenant_id = ?";

    return jdbcTemplate.query(sql, new Object[]{tenantId}, new TenantRowMapper())
            .stream()
            .findFirst();
  }

  private static class TenantRowMapper implements RowMapper<TenantEntity> {
    @Override
    public TenantEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new TenantEntity(
              rs.getLong("tenant_id"),
              rs.getString("name"),
              rs.getTimestamp("date_created").toLocalDateTime(),
              rs.getTimestamp("date_updated").toLocalDateTime()
      );
    }
  }
}
