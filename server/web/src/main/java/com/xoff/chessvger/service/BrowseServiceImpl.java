package com.xoff.chessvger.service;

import com.xoff.chessvger.model.StatBrowser;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.view.StatBrowserView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrowseServiceImpl implements IBrowseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static String getLastMove(String input) {
        if (input == null || input.isEmpty()) return "";

        // Supprime le dernier '#' s'il existe pour éviter un élément vide
        if (input.endsWith("#")) {
            input = input.substring(0, input.length() - 1);
        }

        String[] parts = input.split("#");
        return parts[parts.length - 1];
    }
    public List<StatBrowserView> browse(String previousMoves) {
        List<StatBrowserView> statBrowserViews = new ArrayList<>();



        if (previousMoves == null || previousMoves.isEmpty()) {

            String query = """
            SELECT id, level, white_win, nul, black_win, last_game_date, elo_min, moves_start 
            FROM stat_browser 
            WHERE moves_start = ?
        """;
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
                            return new StatBrowserView(stat, getLastMove(stat.getMovesStart() ));
                        }
                );

                statBrowserViews.addAll(buildw);
            }
        }
        else{
            int level=previousMoves.split("#").length;
            String query = """
            SELECT id, level, white_win, nul, black_win, last_game_date, elo_min, moves_start 
            FROM stat_browser 
            WHERE moves_start like ? and level=?
        """;
            log.info("cas like " + previousMoves + " " + (level+1));
            List<StatBrowserView> buildw = jdbcTemplate.query(
                    query,
                    new Object[]{previousMoves+"%" ,level},
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

                        return new StatBrowserView(stat, getLastMove(stat.getMovesStart() ));
                    }
            );

            statBrowserViews.addAll(buildw);
        }

        return statBrowserViews;
    }

}
