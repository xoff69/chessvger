package com.xoff.chessvger.service;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.model.StatBrowser;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.view.StatBrowserView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrowseServiceImpl implements IBrowseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<StatBrowserView> browse(String previousMoves) {
        List<StatBrowserView> statBrowserViews = new ArrayList<>();

        String query = """
            SELECT id, level, white_win, nul, black_win, last_game_date, elo_min, moves_start 
            FROM stat_browser 
            WHERE moves_start = ?
        """;

        if (previousMoves == null || previousMoves.isEmpty()) {


            for (String s : Constants.ALL_FIRST_MOVE) {
                List<StatBrowserView> buildw = jdbcTemplate.query(
                        query,
                        new Object[]{s + "#"},
                        (rs, rowNum) -> {
                            StatBrowser stat = new StatBrowser();
                            stat.setId(rs.getLong("id"));
                            stat.setLevel(rs.getInt("level"));
                            stat.setBlanc(rs.getInt("white_win"));
                            stat.setNul(rs.getInt("nul"));
                            stat.setNoir(rs.getInt("black_win"));
                            stat.setLastGameDate(rs.getString("last_game_date"));
                            stat.setEloMin(rs.getInt("elo_min"));
                            stat.setMovesStart(rs.getString("moves_start"));
                            return new StatBrowserView(stat, s);
                        }
                );

                statBrowserViews.addAll(buildw);
            }
        }
        else{
            List<StatBrowserView> buildw = jdbcTemplate.query(
                    query,
                    new Object[]{previousMoves },
                    (rs, rowNum) -> {
                        StatBrowser stat = new StatBrowser();
                        stat.setId(rs.getLong("id"));
                        stat.setLevel(rs.getInt("level"));
                        stat.setBlanc(rs.getInt("white_win"));
                        stat.setNul(rs.getInt("nul"));
                        stat.setNoir(rs.getInt("black_win"));
                        stat.setLastGameDate(rs.getString("last_game_date"));
                        stat.setEloMin(rs.getInt("elo_min"));
                        stat.setMovesStart(rs.getString("moves_start"));
                        return new StatBrowserView(stat, previousMoves);
                    }
            );

            statBrowserViews.addAll(buildw);
        }

        return statBrowserViews;
    }

}
