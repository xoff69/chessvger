package com.xoff.chessvger.chess.game;


import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.util.Constants;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameOfAPlayerManager implements IGameOfAPlayerManager {

  private final GameOfAPlayerMap map;



  public GameOfAPlayerManager(DatabaseManager databaseManager) {

    String name =
        databaseManager.getFolder(databaseManager.createName()) + "_GameOfAPlayerMap_GOP_" +
            Constants.MAP_SFX;
    map = new GameOfAPlayerMap(name);

  }

  public void clear() {
    map.clear();
  }


  public List<CommonGame> listGameOfAPlayer(DatabaseManager databaseManager, long idPlayer) {


    List<Long> l1 = map.getValuesForKey(idPlayer);
    List<CommonGame> result = new ArrayList<>();

    l1.forEach((numeroPartie) -> {
      // on cherche dans quel game manager se trouve la partie
      String premier = databaseManager.getGameWhereMapManager().get(numeroPartie);
      log.info(numeroPartie + " premier " + premier);
      ICommonGameManager commonGameManager = databaseManager.getGlobalGameManager().get(premier);
      result.add(commonGameManager.get(numeroPartie));

    });
    log.info("listGameOfAPlayer " + result.size());
    return result;
  }

  public int countGameOfAPlayer(Long idPlayer) {

    return map.sizeSet(idPlayer);
  }


  public void finish() {

    map.commit();
  }

  public void ajoute(long item, long idgame) {

    map.add(item, idgame);
  }
}
