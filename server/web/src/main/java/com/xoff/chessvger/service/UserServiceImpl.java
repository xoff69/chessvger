package com.xoff.chessvger.service;

import com.xoff.chessvger.dao.UserDao;
import com.xoff.chessvger.dao.UtilDao;
import com.xoff.chessvger.model.TenantEntity;
import com.xoff.chessvger.model.UserEntity;
import com.xoff.chessvger.ui.web.controller.tools.UserDTO;
import com.xoff.chessvger.ui.web.controller.tools.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public UserDTO getById(long id) {

        Optional<UserEntity> userEntity = findUserById(id);
        if (!userEntity.isPresent()) {
            throw new RuntimeException("user not found");
        }
        return mapToDTO(userEntity.get());
    }

    public UserDTO getUserByUsername(String username) throws ServiceException {

        Optional<UserEntity> optionalUserEntity = findByLogin(username);
        log.info("getUserByUsername, username: " + username + ", userEntity: " + optionalUserEntity);
        if (optionalUserEntity.isPresent()) {
            return mapToDTO(optionalUserEntity.get());
        }
        throw new ServiceException("user not found");
    }

    public Long count() {

        return 5L; // TODO
    }

    public List<UserDTO> findAll(Pageable pageable) {

        String sql = UtilDao.getAllPaginatedQuery("common.users");

        List<UserEntity> userEntities = jdbcTemplate.query(sql, new Object[]{pageable.getPageSize(), pageable.getOffset()},
                new UserRowMapper());

        return userEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    public UserDTO findByLoginAndPassword(String login, String password) {

        return mapToDTO(repofindByLoginAndPassword(login, password).orElse(null));
    }

    private UserDTO mapToDTO(UserEntity userEntity) {
        return UserMapper.toDto(userEntity);
    }

    private Optional<UserEntity> repofindByLoginAndPassword(String login, String password) {
        String sql = UserDao.SELECT_LOGIN_PASSWORD;

        return jdbcTemplate.query(sql, new Object[]{login, password}, new UserRowMapper())
                .stream()
                .findFirst();
    }

    private Optional<UserEntity> findUserById(long id) {
        String sql = UtilDao.findById("common.users");

        return jdbcTemplate.query(sql, new Object[]{id}, new UserRowMapper())
                .stream()
                .findFirst();
    }


    public List<UserEntity> list() {
        String sql = UtilDao.getAllQuery("common.users");

        return jdbcTemplate.query(sql, new UserRowMapper());
    }



    public Optional<UserEntity> findByLogin(String login) {
        String sql = UserDao.SELECT_BY_LOGIN;
        log.info("findByLogin, login: " + login + " " + sql);
        return jdbcTemplate.query(sql, new Object[]{login}, new UserRowMapper())
                .stream()
                .findFirst();
    }


    public Optional<TenantEntity> getTenant(long tenantId) {
        log.info("getTenant, userId: " + tenantId);
        return findById(tenantId);
    }

    public Optional<TenantEntity> getByUserId(long userId) {
        log.info("getByUserId, userId: " + userId);

        // TODO appeler redis pour avoir le tenantId?
        // on va mettre en place un cache userID -? tenant
        UserDTO user = getById(userId);
        return findById(user.getTenantId());
    }

    private Optional<TenantEntity> findById(long tenantId) {
        String sql = UtilDao.findById("common.tenants", "tenant_id");

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

    // ✅ RowMapper pour mapper un User
    class UserRowMapper implements RowMapper<UserEntity> {
        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            Optional<TenantEntity> tenantEntity = getTenant(rs.getLong("tenant_id"));
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
}
