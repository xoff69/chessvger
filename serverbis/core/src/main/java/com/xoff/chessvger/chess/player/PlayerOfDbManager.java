package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.chess.database.DatabaseManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerOfDbManager implements IPlayerOfDbManager {

  private final PlayerOfDbMap map;

  public PlayerOfDbManager(DatabaseManager databaseManager) {

    map = new PlayerOfDbMap(databaseManager.createName());
  }

  @Override
  public List<Long> listIdsOfPlayer() {
    return map.list();
  }

  public void clear() {
    map.clear();
  }

  public void finish() {

    map.commit();
  }

  public void add(long idplayer) {
    map.add(idplayer);

  }
}
