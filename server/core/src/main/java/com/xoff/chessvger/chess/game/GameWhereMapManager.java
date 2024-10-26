package com.xoff.chessvger.chess.game;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameWhereMapManager implements IGameWhereMapManager {


  private final GameWhereMap map;

  public GameWhereMapManager(String dbName) {
    map = new GameWhereMap(dbName);
  }

  public void clear() {
    map.clear();
  }

  public void add(long key, String value) {
    map.add(key, value);
  }


  public void finish() {
    map.commit();
  }

  public String get(long key) {
    return map.get(key);
  }
}
