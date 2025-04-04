package com.xoff.chessvger.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.dao.GameOfAPlayerDao;
import com.xoff.chessvger.dao.UtilDao;
import com.xoff.chessvger.model.CommonPlayer;
import com.xoff.chessvger.model.PlayerGameCount;
import com.xoff.chessvger.model.PlayerGameModel;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class GamePlayerService {

    private final JdbcTemplate jdbcTemplate;
    private final ApiService apiService;

    private final RowMapper<PlayerGameModel> rowMapper = (rs, rowNum) -> {
        PlayerGameModel game = new PlayerGameModel();
        return game;
    };

    public long count() {

        return jdbcTemplate.queryForObject(UtilDao.getCountQuery("game_of_a_player"), Long.class);
    }

    public Page<PlayerGameCount> getPlayersWithGameCount(Pageable pageable) throws IOException, InterruptedException {
        String sql = GameOfAPlayerDao.SELECT_COMPLEXE;
        List<PlayerGameCount> players = jdbcTemplate.query(
                sql,
                playerGameCountRowMapper(),
                pageable.getPageSize(),
                pageable.getOffset()
        );

        log.info("sql = " + sql);

        String[] ids = players.stream()
                .map(player -> String.valueOf(player.getId()))
                .toArray(String[]::new);
        // http://localhost:8080/apiadmin/players/fetchPlayers?ids=123&ids=456
        log.info("getPlayersWithGameCount ids: {}", ids);
        String allPlayers = apiService.callExternalApi("http://localhost:8080/apiadmin/players/fetchPlayers", ids);
        log.info("all player =" + allPlayers);

        ObjectMapper mapper = new ObjectMapper();

        try {
            ResponseList<CommonPlayer> response = mapper.readValue(
                    allPlayers,
                    new TypeReference<ResponseList<CommonPlayer>>() {
                    }
            );
            System.out.println("Count : " + response.getCount());
            response.getList().forEach(player -> {

                        for (PlayerGameCount playerGameCount : players) {
                            if (playerGameCount.getId() == player.getId()) {
                                playerGameCount.setName(player.getName());
                            }
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        // total : TODO
        int total = 5;
        return new PageImpl<>(players, pageable, total);
    }

    private RowMapper<PlayerGameCount> playerGameCountRowMapper() {
        return (rs, rowNum) -> new PlayerGameCount(
                rs.getLong("id"), "",
                rs.getInt("game_count")
        );
    }
}
