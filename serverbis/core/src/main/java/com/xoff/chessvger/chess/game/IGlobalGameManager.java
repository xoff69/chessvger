package com.xoff.chessvger.chess.game;

import java.util.List;

public interface IGlobalGameManager {

  void update();


  List<CommonGame> getAllGamesReadOnly();

  int size();

  ICommonGameManager get(String first);

  void finish();

  void clear();
}
