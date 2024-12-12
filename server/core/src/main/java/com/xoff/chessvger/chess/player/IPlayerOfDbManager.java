package com.xoff.chessvger.chess.player;

import java.util.List;

public interface IPlayerOfDbManager {
  List<Long> listIdsOfPlayer();

  void clear();


  void finish();

  void add(long idplayer);
}
