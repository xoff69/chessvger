package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonGameManager implements ICommonGameManager {

  private final MapGame mapGame;


  public CommonGameManager(DatabaseManager databaseManager, String base) {
    mapGame = new MapGame(databaseManager, base + "G");
  }

  public static List<CommonGame> removeDuplicate(List<CommonGame> li) {

    return new ArrayList<>(new HashSet(li));
  }

  public static List<CommonGame> restrictSize(List<CommonGame> li, int size) {

    if (li.size() > size) {
      return li.subList(0, size);
    } else {
      return li;
    }
  }


  public void update() {
    mapGame.update();
  }


  public void clear() {
    mapGame.clear();
    mapGame.update();
  }


  public void finish() {

    mapGame.commit();
  }


  public List<CommonGame> getGameByStart(String[] previousMoves) {
    return mapGame.getGameByStart(previousMoves);
  }


  public void upsert(CommonGame g, DBOperation operation) {

    mapGame.add(g.getId(), g, operation);
  }

  public int nbgames() {

    List<CommonGame> games = getGames();
    int count = 0;
    for (CommonGame commonGame : games) {
      if (!commonGame.isDeleted()) {
        count++;
      }
    }
    return count;
  }

  public List<CommonGame> getGames() {
    return mapGame.getGames();
  }

  public CommonGame get(long id) {
    return mapGame.get(id);
  }


  public List<CommonGame> search(Filter filter) {
    return mapGame.search(filter);
  }

}
