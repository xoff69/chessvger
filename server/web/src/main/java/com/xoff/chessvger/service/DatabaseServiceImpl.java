package com.xoff.chessvger.service;

import com.xoff.chessvger.dao.UtilDao;
import com.xoff.chessvger.database.DatabaseHelperService;
import com.xoff.chessvger.database.DynamicDataSourceService;
import com.xoff.chessvger.model.DatabaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DatabaseServiceImpl implements IDatabaseService {
    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DatabaseHelperService databaseHelperService;

    public Long count() {
        String query = UtilDao.getCountQuery("common.databases");
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    public DatabaseModel findById(Long id) {
        try {
            String query = UtilDao.findById("common.databases");
            return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                DatabaseModel model = new DatabaseModel();
                model.setId(rs.getLong("id"));
                model.setName(rs.getString("name"));
                // TODO
                return model;
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
            databaseHelperService.trace();
            return null;
        }
    }

    public List<DatabaseModel> findAll() {
        try {

            String query = UtilDao.getAllQuery("common.databases");
            return jdbcTemplate.query(query, (rs, rowNum) -> {
                DatabaseModel model = new DatabaseModel();
                model.setId(rs.getLong("id"));
                model.setName(rs.getString("name"));
                // Ajoutez d'autres champs selon votre modèle
                return model;
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
            databaseHelperService.trace();
            throw new RuntimeException(e);
        }
    }
}
