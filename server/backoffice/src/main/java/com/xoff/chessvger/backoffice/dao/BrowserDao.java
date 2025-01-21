package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.chess.player.ICommonPlayerManager;
import com.xoff.chessvger.chess.stat.StatBrowser;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.DateUtils;
import com.xoff.chessvger.util.PgnUtil;

public class BrowserDao
{
  /*
  public void browseFirstMove(List<CommonGame> liste) {

    ICommonPlayerManager playerManager = GlobalManager.getInstance().getCommonPlayerManager();
    StatBrowserRepository statBrowserRepository = ...; // Injectez votre repository ici
    GameOfAStatRepository gameOfAStatRepository = ...; // Injectez votre repository ici

    for (CommonGame g : liste) {
      String debutS = StringUtils.EMPTY;

      // Extraction des mouvements
      String[] moves = PgnUtil.extractMovesFromString(g.getMoves());
      int max = inMemory ? moves.length + 1 : ParamConstants.MAX_FIRST_MOVE;

      for (int i = 0; i < max; i++) {
        if (i >= moves.length) {
          break;
        }
        debutS = debutS + moves[i] + Constants.MAP_SEP;

        // Récupération ou création de l'entrée StatBrowser
        StatBrowser sb = statBrowserRepository.findByDebutS(debutS)
            .orElse(new StatBrowser());

        if (sb.getId() == null) {
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
        if (DateUtils.getYear(g.getDate()) > DateUtils.getYear(sb.getLastGameDate())) {
          sb.setLastGameDate(g.getDate());
        }

        // Ajout des meilleurs joueurs
        String blanc = g.getNomBlanc();
        if (playerManager.isWellKnowPlayer(blanc)) {
          sb.addBestPlayer(blanc);
        }
        String noir = g.getNomNoir();
        if (playerManager.isWellKnowPlayer(noir)) {
          sb.addBestPlayer(noir);
        }

        // Mise à jour du niveau et de l'elo minimum
        sb.setLevel(i);
        int eloBlanc = g.getWhiteElo() == 0 ? Integer.MAX_VALUE : g.getWhiteElo();
        int eloNoir = g.getBlackElo() == 0 ? Integer.MAX_VALUE : g.getBlackElo();
        sb.setEloMin(Math.min(Math.min(eloBlanc, eloNoir), sb.getEloMin()));

        // Sauvegarde dans la base de données
        statBrowserRepository.save(sb);

        // Ajout de l'association entre StatBrowser et CommonGame
        GameOfAStat gameOfAStat = new GameOfAStat();
        gameOfAStat.setStatId(sb.getId());
        gameOfAStat.setGameId(g.getId());
        gameOfAStatRepository.save(gameOfAStat);
      }
    }
  }
*/
}
