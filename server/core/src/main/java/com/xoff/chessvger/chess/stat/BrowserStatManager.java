package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.GameOfAStatMap;
import com.xoff.chessvger.chess.game.ICommonGameManager;
import com.xoff.chessvger.chess.player.ICommonPlayerManager;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.DateUtils;
import com.xoff.chessvger.util.PgnUtil;
import com.xoff.chessvger.view.StatBrowserView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@ToString
public class BrowserStatManager implements IBrowserStatManager {
  @SuppressFBWarnings(value = {"EI_EXPOSE_REP2"}, justification = "We want that")
  private final DatabaseManager databaseManager;
  private final StatBrowserMap browserMap;
  private final GameOfAStatMap gameOfAStatMap;

  private final String premier;


  private boolean inMemory = false;


  public BrowserStatManager(String premier, DatabaseManager databaseManager, boolean inMemory) {
    this.databaseManager = databaseManager;
    this.inMemory = inMemory;
    this.browserMap = new StatBrowserMap(premier, databaseManager.createName(), inMemory);
    this.premier = premier;
    gameOfAStatMap = new GameOfAStatMap(premier, databaseManager.createName(), inMemory);

  }

  public List<CommonGame> getGames(Position p) {
    //  log.info("getGames  :" );
    List<CommonGame> res = new ArrayList();
    List<StatBrowserView> lsb = getBrowseData(databaseManager, p.getMoves());

    ICommonGameManager cm = databaseManager.getGlobalGameManager().get(premier);
///log.info("getGames:" +lsb.size());
    for (StatBrowserView sb : lsb) {
      //   log.info("sb "+sb);
      List<Long> li = gameOfAStatMap.getValuesForKey(sb.getId());
      if (li != null) {
        //      log.info("sb= " + sb + "-" + li.size());
        for (long id : li) {
          CommonGame g = cm.get(id);
          if (g != null) {
            res.add(g);
          }
        }
      } else {
        log.error("pas de game pour la clef :" + sb);
      }
    }
    //    log.info("getGames:" + res.size());
    return res;
  }


  public void clear() {
    gameOfAStatMap.clear();
    browserMap.clear();
  }


  public void finish() {
    browserMap.commit();
    gameOfAStatMap.commit();
  }

  /**
   * on ecrit sur le disque OU EN MEMOIRE les stats pour les prmiers coups c
   * est borné a un MAX a definir, on remplit une hashmap de StatBrowserDB et
   * on l ecrit sur le disque a la fin
   */
  public void browseFirstMove(List<CommonGame> liste) {

    ICommonPlayerManager playerManager = GlobalManager.getInstance().getCommonPlayerManager();
    //  log.info("browseFirstMove="+ liste.size());
    for (CommonGame g : liste) {
      String debutS = StringUtils.EMPTY;
      // log.info("browseFirstMove="+ g);
      // @FIXME ne doit pas gerer les variantes
      String[] moves = PgnUtil.extractMovesFromString(g.getMoves());
      int max = ParamConstants.MAX_FIRST_MOVE;
      if (inMemory) {
        max = moves.length + 1;
      }
      for (int i = 0; i < max; i++) {
        if (i >= moves.length) {
          break;
        }
        debutS = debutS + moves[i] + Constants.MAP_SEP;
        StatBrowser sb = browserMap.get(debutS);

        if (sb == null) {
          sb = new StatBrowser();
          sb.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
        } else {
          //xxx est ce que des fois on a ps nul ?
          //     log.info("non nul"+debutS);
        }
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
        //  log.info("game : "+DateUtils.getYear(g.getDate())+"-"+DateUtils.getYear(sb.getLastGameDate()));
        if (DateUtils.getYear(g.getDate()) > DateUtils.getYear(sb.getLastGameDate())) {
          sb.setLastGameDate(g.getDate());
        }
        String blanc = g.getNomBlanc();

        if (playerManager.isWellKnowPlayer(blanc)) {
          sb.addBestPlayer(blanc);
        }
        String noir = g.getNomNoir();

        if (playerManager.isWellKnowPlayer(noir)) {
          sb.addBestPlayer(noir);
          //     log.info("ok nom ="+noir+"-"+premier);
        }
        sb.setLevel(i);
        int eloBlanc = g.getWhiteElo();
        int eloNoir = g.getBlackElo();
        if (eloBlanc == 0) {
          eloBlanc = Integer.MAX_VALUE;
        }
        if (eloNoir == 0) {
          eloNoir = Integer.MAX_VALUE;
        }
        int min = Math.min(eloBlanc, eloNoir);
        min = Math.min(min, sb.getEloMin());
        sb.setEloMin(min);

        browserMap.add(debutS, sb);
        gameOfAStatMap.add(sb.getId(), g.getId());
        //    log.info("debutS="+debutS+"addDatabaseManager debutS="+sb.getId() + "---"+g.getId());
      }
    }
    //   log.info("<browseFirstMove=>" + premier + " " + browserMap.size());

  }

  /**
   * appele par l'IHM appelle findNStat
   */
  public List<StatBrowserView> getBrowseData(DatabaseManager databaseManager,
                                             List<String> pastMoves) {
    List<StatBrowserView> listStatBrowserView = new ArrayList();
    int level = pastMoves.size();
    int max = ParamConstants.MAX_FIRST_MOVE;
    if (inMemory) {
      max = pastMoves.size() + 1;
    }
    if (pastMoves.size() < max) {

      if (level == 0) {
        //     log.info("aa" + max+"-"+pastMoves.size() );
        // // on va boucler sur tous les premiers coups possibles
        for (StatBrowser s : browserMap.values()) {
          if (s.getLevel() == 0) {
            StatBrowserView sv = new StatBrowserView(s, premier);
            listStatBrowserView.add(sv);
          }
        }
      } else {
        // on constitue la clef par rapport aux pastMove
        // si level=1 , on prend toutes les values
        // exemple pastMove contient {1.e4} on vaut toutes les reponses,
        // donc tous les level=2 tel que la clef commence par 1.e4

        StringBuilder sbb = new StringBuilder(pastMoves.get(0));

        for (int i = 1; i < pastMoves.size(); i++) {
          sbb.append(Constants.MAP_SEP).append(pastMoves.get(i));
        }
        String prefixe = sbb.toString();
        for (String clef : browserMap.keys()) {
          StatBrowser s = browserMap.get(clef);
          //  log.info(level+ "getBrowseData" + clef + "-" + s);

          if (s.getLevel() == level) {
            //   log.info("getBrowseData::" + clef+"--"+prefixe);
            if (clef.startsWith(prefixe)) {
              //   log.info("getBrowseData" +clef +"--"+(prefixe.length()+1));
              String cl = clef.substring(prefixe.length() + 1);
              cl = cl.substring(0, cl.length() - 1);
              StatBrowserView sv = new StatBrowserView(s, cl);
              //    log.info("ajout sv");
              listStatBrowserView.add(sv);
            }
          }
        }
      }

    } else {
      String[] s = new String[pastMoves.size()];
      int i = 0;
      for (String sx : pastMoves) {
        s[i++] = sx;
      }

      listStatBrowserView = findNStat(s);
    }
    return listStatBrowserView;
  }

  private List<StatBrowserView> findNStat(String[] listMove) {

    ICommonGameManager gameManager =
        GlobalManager.getInstance().getDatabaseManager(databaseManager.getDatabaseId())
            .getGlobalGameManager().get(premier);

    List<CommonGame> games = gameManager.getGameByStart(listMove);
    // boucler sur les moves pour avoir le debut de chaine ci dessus
    HashMap<String, StatBrowserView> hashStat = new HashMap<>();
    // pour tous les resultats, on va construire la stat
    // System.out.println("findNStat on boucle sur :" + games.size());
    for (CommonGame game : games) {
      // on va chercher le ieme coup du game
      if (listMove.length >= game.getMoves().length()) { // correction bug
        continue;
      }
      log.info(">findNStat " + listMove.length + " -" + listMove + "//" + game.getMoves().length());
      String ieme = PgnUtil.extractMovesFromString(game.getMoves())[listMove.length];
      //   System.out.println("ieme :" + ieme);
      StatBrowserView sbv = hashStat.get(ieme);
      if (sbv == null) {
        sbv = new StatBrowserView();
        sbv.setCoup(ieme);
      }
      switch (game.getResult()) {
        case Constants.RESULT_1_0:
          sbv.setBlanc(sbv.getBlanc() + 1);
          break;
        case Constants.RESULT_0_1:
          sbv.setNul(sbv.getNul() + 1);
          break;
        default:
          sbv.setNoir(sbv.getNoir() + 1);
          break;
      }
      hashStat.put(ieme, sbv);
    }
    games.clear();
    List<StatBrowserView> result = new ArrayList<>();
    for (StatBrowserView value : hashStat.values()) {
      result.add(value);
    }
    return result;
  }
}
