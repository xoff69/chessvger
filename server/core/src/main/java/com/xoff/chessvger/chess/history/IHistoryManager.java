package com.xoff.chessvger.chess.history;

import com.xoff.chessvger.chess.game.CommonGame;
import java.util.List;

public interface IHistoryManager {


  List<CommonGame> listHistory();

  void clear();

  void add(Long value);


  void finish();

}
