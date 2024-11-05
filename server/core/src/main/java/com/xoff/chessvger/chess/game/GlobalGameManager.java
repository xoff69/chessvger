package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalGameManager implements IGlobalGameManager {

  private final HashMap<String, ICommonGameManager> routeur;

  private IGameWhereMapManager gameWhereMapManager;
  private IGameOfAPlayerManager gameOfAPlayerManager;

  public static PageView computePaging(List<CommonGame> games, Pageable paging){
    // FIXME
    log.error("NOT IMPLEMENTED computePaging");
    return null;
  }
  public GlobalGameManager(DatabaseManager databaseManager) {
    routeur = new HashMap();
    this.gameOfAPlayerManager = databaseManager.getGameOfAPlayerManager();
    this.gameOfAPlayerManager = databaseManager.getGameOfAPlayerManager();
    for (String s : Constants.ALL_FIRST_MOVE) {
      ICommonGameManager gameM =
          new CommonGameManager(databaseManager, databaseManager.createName() + "_" + s);
      routeur.put(s, gameM);
    }
  }



  public void update() {
    //  log.info("update");
    for (String s : Constants.ALL_FIRST_MOVE) {
      ICommonGameManager cg = routeur.get(s);
      // log.info("update "+cg);
      cg.update();

    }
  }

  public List<CommonGame> getAllGamesReadOnly() {

    List<CommonGame> games = new ArrayList<>();
    for (String s : Constants.ALL_FIRST_MOVE) {
      ICommonGameManager cg = routeur.get(s);
      games.addAll(cg.getGames());
    }
    log.info("getAllGamesReadOnly " + games.size());
    return games;
  }

  public int size() {
    int compteur = 0;
    for (String s : Constants.ALL_FIRST_MOVE) {
      ICommonGameManager cg = routeur.get(s);
      compteur += cg.nbgames();
    }
    return compteur;
  }


  public ICommonGameManager get(String first) {
    return routeur.get(first);
  }


  /**
   * termine les CommonGameManager
   */

  public void finish() {
    //   log.info("commit globalManager.ameManager");
    for (String s : Constants.ALL_FIRST_MOVE) {
      //  log.info("s ="+s+"-"+routeur.getDatabaseManager(s));
      routeur.get(s).finish();
    }
  }


  public void clear() {

    // log.info("clear globalManager.);
    for (String s : Constants.ALL_FIRST_MOVE) {
      routeur.get(s).clear();
    }
  }
}
