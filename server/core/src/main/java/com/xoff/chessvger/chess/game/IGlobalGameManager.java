package com.xoff.chessvger.chess.game;

import java.util.List;

public interface IGlobalGameManager {

  public void update();


  public List<CommonGame> getAllGamesReadOnly();

  public int size();

  public ICommonGameManager get(String first);

  public void finish();

  public void clear();
}
