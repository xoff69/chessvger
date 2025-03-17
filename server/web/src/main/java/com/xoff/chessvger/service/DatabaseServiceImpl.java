package com.xoff.chessvger.service;

import com.xoff.chessvger.model.DatabaseModel;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseServiceImpl implements IDatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DatabaseHelperService databaseHelperService;

    public Long count() {
        String query = "SELECT COUNT(*) FROM databases"; // Utilisation du nom de table "databases"
        return jdbcTemplate.queryForObject(query, Long.class);
    }
    public DatabaseModel findById(Long id) {
        try {
            String query = "SELECT * FROM databases WHERE id = ?"; // Utilisation du nom de table "databases"
            return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                DatabaseModel model = new DatabaseModel();
                model.setId(rs.getLong("id"));
                model.setName(rs.getString("name"));
                //TODO  Ajoutez d'autres champs selon votre modèle
                return model;
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
            databaseHelperService.trace(); throw new RuntimeException(e);
        }
    }
    public List<DatabaseModel> findAll() {
        try {
            String query = "SELECT * FROM databases"; // Utilisation du nom de table "databases"
            return jdbcTemplate.query(query, (rs, rowNum) -> {
                DatabaseModel model = new DatabaseModel();
                // Remplissez le modèle avec les données de la base de données
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
