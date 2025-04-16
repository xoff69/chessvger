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

    private static final String INSERT_SQL = "INSERT INTO %s.stat_browser "
            + "(level, white_win, nul, black_win, last_game_date, elo_min, moves_start) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

    private static long insertStat(Connection connection, String schemaName, int level, int whiteWin, int nul, int blackWin, String lastGameDate, int eloMin, String movesStart) throws SQLException {
        String query = String.format(INSERT_SQL, schemaName);
        long generatedId = -1;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, level);
            stmt.setInt(2, whiteWin);
            stmt.setInt(3, nul);
            stmt.setInt(4, blackWin);
            stmt.setString(5, lastGameDate);
            stmt.setInt(6, eloMin);
            stmt.setString(7, movesStart);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    generatedId = rs.getLong("id");
                    log.info("Insert successful! stat_browser - Generated ID: " + generatedId);
                }
            }
        } catch (SQLException e) {
            log.error("Insert failed: " + e.getMessage());
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

                if (sb.getId() == 0) {
                    sb.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
                }

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

                long id=insertStat(connection, schemaName, sb.getLevel(), sb.getBlanc(), sb.getNul(), sb.getNoir(), sb.getLastGameDate(), sb.getEloMin(),debutS);

                GameOfStatDao.insert(connection, schemaName, g.getId(), id);
            }
        }

    }
}
