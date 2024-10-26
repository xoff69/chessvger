package com.xoff.chessvger.chess.game;

public interface IGameWhereMapManager {

  public void clear();

  public void add(long key, String value);


  public void finish();

  public String get(long key);
}
