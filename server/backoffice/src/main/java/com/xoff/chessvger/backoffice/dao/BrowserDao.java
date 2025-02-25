package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.game.CommonGame;
import com.xoff.chessvger.chess.player.ICommonPlayerManager;
import com.xoff.chessvger.chess.stat.StatBrowser;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.DateUtils;
import com.xoff.chessvger.util.PgnUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BrowserDao
{

  private static final String INSERT_SQL = "INSERT INTO %s.stat_browser "
          + "(level, white_win, nul, black_win, last_game_date, elo_min) "
          + "VALUES (?, ?, ?, ?, ?, ?)";

  private static void insertStat(Connection connection,String schemaName, int level, int whiteWin, int nul, int blackWin, String lastGameDate, int eloMin) {
    String query = String.format(INSERT_SQL, schemaName);
try{
         PreparedStatement stmt = connection.prepareStatement(query) ;

      stmt.setInt(1, level);
      stmt.setInt(2, whiteWin);
      stmt.setInt(3, nul);
      stmt.setInt(4, blackWin);
      stmt.setString(5, lastGameDate);
      stmt.setInt(6, eloMin);

      stmt.executeUpdate();
      System.out.println("Insert successful!");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static Optional<StatBrowser> findByDebutS(String debut){
    log.warn("NOT IMPLEMENTED YET: findByDebutS");

  return Optional.of(new StatBrowser()); // TODO
}
  public static void browseFirstMove(Connection connection,String schemaName, List<CommonGame> liste) throws SQLException {


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
        StatBrowser sb = findByDebutS(debutS)
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

        // Mise à jour de la dernière date du jeu
        if (DateUtils.getYear(String.valueOf(g.getDate())) > DateUtils.getYear(sb.getLastGameDate())) {
          sb.setLastGameDate(String.valueOf(g.getDate()));
        }

        // Ajout des meilleurs joueurs
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

          insertStat(connection, schemaName, sb.getLevel(),sb.getBlanc(),sb.getNul(),sb.getNoir(),sb.getLastGameDate(),sb.getEloMin());

        GameOfStatDao.insert(connection, schemaName, g.getId(), sb.getId());
      }
    }

  }}
