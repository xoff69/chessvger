package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.QuickFilter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.ICommonGameManager;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.view.StatBrowserView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalBrowserStatManager implements IGlobalBrowserStatManager {
  private final HashMap<String, IBrowserStatManager> routeurTotal;
  private final HashMap<String, IBrowserStatManager> routeurMem;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We want that")
  private final DatabaseManager databaseManager;


  private QuickFilter saved;


  public GlobalBrowserStatManager(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
    routeurTotal = new HashMap();
    routeurMem = new HashMap();
    saved = new QuickFilter(new Position());
  }

  public void put(String first, IBrowserStatManager b) {
    routeurTotal.put(first, b);
  }


  public IBrowserStatManager get(String first) {
    return routeurTotal.get(first);
  }


  public void finish() {
    for (String s : Constants.ALL_FIRST_MOVE) {
      IBrowserStatManager bs = routeurTotal.get(s);
      if (bs != null) {
        routeurTotal.get(s).finish();
      }
      // log.info("browser stat " + s + "-" + bs);

      IBrowserStatManager sb = routeurMem.get(s);
      if (sb != null) {
        routeurMem.get(s).finish();
      }
      //         log.info("browser stat  meme" + s + "-" + bs);

    }
  }


  public void clear() {

    for (String s : Constants.ALL_FIRST_MOVE) {
      IBrowserStatManager sb = routeurMem.get(s);
      if (sb != null) {
        routeurMem.get(s).clear();
      }
    }
    routeurMem.clear();
  }


  public List<CommonGame> gameOfASB(QuickFilter qf) {

    List<CommonGame> resultat = new ArrayList();

    if (qf.isEmpty()) {
      if (qf.isMemory()) {
        for (String move : Constants.ALL_FIRST_MOVE) {
          IBrowserStatManager bsm = routeurMem.get(move);
          if (bsm != null) {
            List<CommonGame> l = bsm.getGames(qf.getPosition());
            if (l != null) {
              resultat.addAll(l);
            }
          }
        }
      } else {
        log.info("getListSBForFilter:" + qf + "--> recherche dans les donnees sur disque");
        // les donnees du disue
        for (String move : Constants.ALL_FIRST_MOVE) {
          IBrowserStatManager bsm = routeurTotal.get(move);
          List<CommonGame> l = bsm.getGames(qf.getPosition());
          if (l != null) {
            resultat.addAll(l);
          }
        }
      }
    } else {

      log.info("getListSBForFilter:" + qf + "--> recherche dans les donnees en memoire//CACHE");
      for (String move : Constants.ALL_FIRST_MOVE) {
        IBrowserStatManager bsm = routeurMem.get(move);
        List<CommonGame> l = bsm.getGames(qf.getPosition());
        if (l != null) {
          resultat.addAll(l);
        }
      }
    }
    return resultat;
  }


  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We want that")
  public List<StatBrowserView> getListSBForFilter(QuickFilter qf) {
    log.info("getListSBForFilter:" + qf);
    List<StatBrowserView> la = new ArrayList();
    DatabaseManager dm = databaseManager;
    if (qf.isEmpty()) {
      log.info("getListSBForFilter:" + qf + "--> recherche dans les donnees sur disque");
      if (qf.isMemory()) {
        for (String move : Constants.ALL_FIRST_MOVE) {
          IBrowserStatManager bsm = routeurMem.get(move);
          if (bsm != null) {
            List<StatBrowserView> l =
                bsm.getBrowseData(databaseManager, qf.getPosition().getMoves());
            //    log.info("move ="+l);
            if (l != null) {
              la.addAll(l);
            }
          }
        }
      } else {
        // les donnees du disue
        for (String move : Constants.ALL_FIRST_MOVE) {
          IBrowserStatManager bsm = routeurTotal.get(move);

          List<StatBrowserView> l = bsm.getBrowseData(databaseManager, qf.getPosition().getMoves());
          //    log.info("move ="+l);
          if (l != null) {
            la.addAll(l);
          }
        }
      }

    } else {

      if (saved.equals(qf)) {
        log.info("getListSBForFilter:" + qf + "--> recherche dans les donnees en memoire//CACHE");
        for (String move : Constants.ALL_FIRST_MOVE) {
          IBrowserStatManager bsm = routeurMem.get(move);

          List<StatBrowserView> l = bsm.getBrowseData(databaseManager, qf.getPosition().getMoves());
          if (l != null) {
            la.addAll(l);
          }
        }
      } else {

        log.info("getListSBForFilter:" + qf +
            "--> constructeur puis recherche dans les donnees en memoire");
        clear();
        saved = qf;

        try {

          for (String move : Constants.ALL_FIRST_MOVE) {
            BrowserStatManager bsm = new BrowserStatManager(move, databaseManager, true);
            ICommonGameManager cm = dm.getGlobalGameManager().get(move);
            // le filtrage doit se faire la
            List<CommonGame> lg = cm.getGames();
            List<CommonGame> lf = new ArrayList<>();

            lg.stream().filter((g) -> (qf.acceptGame(g))).forEachOrdered((g) -> {
              lf.add(g);
            });
            log.info("avant =" + lg.size() + "-" + lf.size());
            bsm.browseFirstMove(lf);
            List<StatBrowserView> l =
                bsm.getBrowseData(databaseManager, qf.getPosition().getMoves());
            if (l != null) {
              la.addAll(l);
            }
            //LOGGER.info("avant ="+move+"-"+la.size());
            routeurMem.put(move, bsm);
          }
        } catch (Exception e) {
          log.error(e.getMessage());
        }
      }
    }
    return la;
  }


  public List<StatBrowserView> buildTreeFromGame(List<CommonGame> games) {

    log.info("buid Tree From gAmes " + games.size());
    List<StatBrowserView> la = new ArrayList();
    clear(); // vide celui en memoire

    try {
      for (String first : Constants.ALL_FIRST_MOVE) {
        List<CommonGame> lgFirstMove = new ArrayList();
        BrowserStatManager bsm = new BrowserStatManager(first, databaseManager, true);

        for (CommonGame cg : games) {
          String move = cg.getFirstMove();
          if (move.equals(first)) {

            lgFirstMove.add(cg);

          }
        } // fin parcours sur les games
        if (!lgFirstMove.isEmpty()) {

          routeurMem.put(first, bsm);
          bsm.browseFirstMove(lgFirstMove);

          List<StatBrowserView> l = bsm.getBrowseData(databaseManager, new ArrayList());

          if (l != null) {
            log.info("ajout " + l + "-" + l.size());
            la.addAll(l);
          }
        }
      } // fin parcours sur les first
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.info("nb stat =" + la.size());
    return la;
  }
}