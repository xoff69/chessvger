package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.filter.Filter;
import java.util.List;

public interface ICommonGameManager {


  void update();

  void clear();


  void finish();


  List<CommonGame> getGameByStart(String[] previousMoves);

  void upsert(CommonGame g, DBOperation operation);

  int nbgames();

  List<CommonGame> getGames();

  CommonGame get(long id);

  List<CommonGame> search(Filter filter);
}
