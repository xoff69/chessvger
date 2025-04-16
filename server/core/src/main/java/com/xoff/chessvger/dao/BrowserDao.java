package com.xoff.chessvger.dao;

import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.model.CommonGame;
import com.xoff.chessvger.model.StatBrowser;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.DateUtils;
import com.xoff.chessvger.util.PgnUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BrowserDao {

    private static final String INSERT_SQL_NO_ID = "INSERT INTO %s.stat_browser "
            + "(level, white_win, nul, black_win, last_game_date, elo_min, moves_start) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?) "
            + "RETURNING id";

    private static final String UPSERT_SQL_WITH_ID = "INSERT INTO %s.stat_browser "
            + "(id, level, white_win, nul, black_win, last_game_date, elo_min, moves_start) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
            + "ON CONFLICT (id) DO UPDATE SET "
            + "level = EXCLUDED.level, "
            + "white_win = EXCLUDED.white_win, "
            + "nul = EXCLUDED.nul, "
            + "black_win = EXCLUDED.black_win, "
            + "last_game_date = EXCLUDED.last_game_date, "
            + "elo_min = EXCLUDED.elo_min, "
            + "moves_start = EXCLUDED.moves_start "
            + "RETURNING id";

    private static long upsert(Connection connection, String schemaName, int level, int whiteWin, int nul, int blackWin, String lastGameDate, int eloMin, String movesStart, long statId) throws SQLException {
        String query;
        boolean insertWithoutId = statId == 0;

        if (insertWithoutId) {
            query = String.format(INSERT_SQL_NO_ID, schemaName);
        } else {
            query = String.format(UPSERT_SQL_WITH_ID, schemaName);
        }

        long generatedId = -1;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int i = 1;
            if (!insertWithoutId) {
                stmt.setLong(i++, statId); // set id first
            }

            stmt.setInt(i++, level);
            stmt.setInt(i++, whiteWin);
            stmt.setInt(i++, nul);
            stmt.setInt(i++, blackWin);
            stmt.setString(i++, lastGameDate);
            stmt.setInt(i++, eloMin);
            stmt.setString(i++, movesStart);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    generatedId = rs.getLong("id");
                    log.info((insertWithoutId ? "Insert" : "Upsert") + " successful! stat_browser - ID: " + generatedId);
                }
            }
        } catch (SQLException e) {
            log.error("Upsert failed: " + e.getMessage());
            throw e;
        }

        return generatedId;
    }



    private static Optional<StatBrowser> findByDebutS(Connection connection, String schemaName, String movesStart) throws SQLException {
        String query = String.format("SELECT id, level, white_win, nul, black_win, last_game_date, elo_min, moves_start FROM %s.stat_browser WHERE moves_start = ?", schemaName);

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, movesStart);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    StatBrowser stat = new StatBrowser();
                    stat.setId(rs.getLong("id"));
                    stat.setLevel(rs.getInt("level"));
                    stat.setBlanc(rs.getInt("white_win"));
                    stat.setNul(rs.getInt("nul"));
                    stat.setNoir(rs.getInt("black_win"));
                    stat.setLastGameDate(rs.getString("last_game_date"));
                    stat.setEloMin(rs.getInt("elo_min"));
                    stat.setMovesStart(rs.getString("moves_start"));
                    return Optional.of(stat);
                }
            }
        } catch (SQLException e) {
            log.error("Query failed: " + e.getMessage());
            throw e;
        }

        return Optional.empty();
    }


    public static void createStatsForGames(Connection connection, String schemaName, List<CommonGame> liste) throws SQLException {

        log.info("browseFirstMove " + liste.size() + " " + schemaName);
        for (CommonGame g : liste) {
            String debutS = "";

            // Extraction des mouvements
            String[] moves = PgnUtil.extractMovesFromString(g.getMoves());
            int max = ParamConstants.MAX_FIRST_MOVE;

            for (int i = 0; i < max; i++) {
                if (i >= moves.length) {
                    break;
                }
                debutS = debutS + moves[i] + Constants.MAP_SEP;

                // Récupération ou création de l'entrée StatBrowser
                StatBrowser sb = findByDebutS(connection,schemaName,debutS)
                        .orElse(new StatBrowser());

                // Mise à jour des statistiques en fonction des résultats
                switch (g.getResult()) {
                    case Constants.RESULT_1_0:
                        sb.setBlanc(sb.getBlanc() + 1);
                        break;
                    case Constants.RESULT_0_1:
                        sb.setNul(sb.getNul() + 1);
                        break;
                    default:
                        sb.setNoir(sb.getNoir() + 1);
                        break;
                }

                if (DateUtils.getYear(String.valueOf(g.getDate())) > DateUtils.getYear(sb.getLastGameDate())) {
                    sb.setLastGameDate(String.valueOf(g.getDate()));
                }

                String blanc = g.getWhitePlayer();
                if (PlayerDao.isWellKnowPlayer(blanc)) {
                    sb.addBestPlayer(blanc);
                }
                String noir = g.getBlackPlayer();
                if (PlayerDao.isWellKnowPlayer(noir)) {
                    sb.addBestPlayer(noir);
                }

                // Mise à jour du niveau et de l'elo minimum
                sb.setLevel(i);
                int eloBlanc = g.getWhiteElo() == 0 ? Integer.MAX_VALUE : g.getWhiteElo();
                int eloNoir = g.getBlackElo() == 0 ? Integer.MAX_VALUE : g.getBlackElo();
                sb.setEloMin(Math.min(Math.min(eloBlanc, eloNoir), sb.getEloMin()));

                long id=upsert(connection, schemaName, sb.getLevel(), sb.getBlanc(), sb.getNul(), sb.getNoir(), sb.getLastGameDate(), sb.getEloMin(),debutS,sb.getId());

                GameOfStatDao.insert(connection, schemaName, g.getId(), id);
            }
        }

    }
}
