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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GamePlayerService {

    private final JdbcTemplate jdbcTemplate;
    private final  ApiService apiService;

    private final RowMapper<PlayerGameModel> rowMapper = (rs, rowNum) -> {
        PlayerGameModel game = new PlayerGameModel();
        return game;
    };

    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM game_of_a_player", Long.class);
    }

    public Page<PlayerGameCount> getPlayersWithGameCount(Pageable pageable) throws IOException, InterruptedException {
        String sql = """
           SELECT id_player, "" as name,COUNT(id_game) AS game_count
         FROM main.game_of_a_player
         GROUP BY id_player
         HAVING COUNT(id_game) > 0
         ORDER BY game_count DESC
        """;


        List<PlayerGameCount> players = jdbcTemplate.query(sql, playerGameCountRowMapper(),
                pageable.getPageSize(), pageable.getOffset());

        // TODO: faire un appel pour aller chercher les joueurs
        // attention au jdbc template
        // utiliser r[1,2,3};
        String ids[]={"1","23"}; // TODO
        String allPlayers=apiService.callExternalApi("http://localhost:8080//apiadmin/players/fetchPlayers",ids);
        log.info("all player ="+allPlayers);
        // total : TODO
        int total=5;
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
