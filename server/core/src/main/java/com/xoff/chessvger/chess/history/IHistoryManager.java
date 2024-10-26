package com.xoff.chessvger.chess.history;

import com.xoff.chessvger.chess.game.CommonGame;
import java.util.List;

public interface IHistoryManager {


  public List<CommonGame> listHistory();

  public void clear();

  public void add(Long value);


  public void finish();

}
