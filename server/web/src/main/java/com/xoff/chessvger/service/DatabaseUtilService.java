package com.xoff.chessvger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseUtilService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> listTables(String schema) {
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schema + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("table_name"));
    }

    public String getCurrentDatabase() {
        return jdbcTemplate.queryForObject("SELECT current_database()", String.class);
    }

    public String getCurrentSchema() {
        return jdbcTemplate.queryForObject("SELECT current_schema()", String.class);
    }
}