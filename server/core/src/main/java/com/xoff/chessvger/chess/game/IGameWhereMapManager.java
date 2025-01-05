package com.xoff.chessvger.chess.game;

public interface IGameWhereMapManager {

  void clear();

  void add(long key, String value);


  void finish();

  String get(long key);
}
