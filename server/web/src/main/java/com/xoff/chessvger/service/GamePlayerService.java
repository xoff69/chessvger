package com.xoff.chessvger.service;

import com.xoff.chessvger.model.CommonGameModel;
import com.xoff.chessvger.model.PlayerGameCount;
import com.xoff.chessvger.model.PlayerGameModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GamePlayerService {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<PlayerGameModel> rowMapper = (rs, rowNum) -> {
        PlayerGameModel game = new PlayerGameModel();
        return game;
    };

    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM game_of_a_player", Long.class);
    }

    public Page<PlayerGameCount> getPlayersWithGameCount(Pageable pageable) {
        String sql = """
            SELECT p.id, p.name, COUNT(g.id_game) AS game_count 
            FROM common.common_player p
            LEFT JOIN game_of_a_player g ON p.id = g.id_player
            GROUP BY p.id, p.name
            ORDER BY game_count DESC
            LIMIT ? OFFSET ?;
        """;

        /*
        String sql = """
    SELECT p.id, p.name, COUNT(g.id_game) AS game_count
    FROM dblink('host=localhost:5432 dbname=chessvger user=chessvger password=chessvger',
                'SELECT id, name FROM common.common_player')
    AS p(id INT, name TEXT)
    LEFT JOIN game_of_a_player g ON p.id = g.id_player
    GROUP BY p.id, p.name
    ORDER BY game_count DESC
    LIMIT ? OFFSET ?;
""";
         */

        // Récupérer les résultats paginés
        List<PlayerGameCount> players = jdbcTemplate.query(sql, playerGameCountRowMapper(),
                pageable.getPageSize(), pageable.getOffset());

        // Récupérer le total des joueurs pour la pagination
        int total = jdbcTemplate.queryForObject("""
            SELECT COUNT(*) FROM common.common_player
        """, Integer.class);

        return new PageImpl<>(players, pageable, total);
    }

    private RowMapper<PlayerGameCount> playerGameCountRowMapper() {
        return (rs, rowNum) -> new PlayerGameCount(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("game_count")
        );
    }
}
