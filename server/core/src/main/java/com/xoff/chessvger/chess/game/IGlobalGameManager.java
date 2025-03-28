package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.model.CommonGame;

import java.util.List;

public interface IGlobalGameManager {

  void update();


  List<CommonGame> getAllGamesReadOnly();

  int size();

  ICommonGameManager get(String first);

  void finish();

  void clear();
}
