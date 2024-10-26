package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.filter.Filter;
import java.util.List;

public interface ICommonGameManager {


  public void update();

  public void clear();


  public void finish();


  public List<CommonGame> getGameByStart(String[] previousMoves);

  public void upsert(CommonGame g, DBOperation operation);

  public int nbgames();

  public List<CommonGame> getGames();

  public CommonGame get(long id);

  public List<CommonGame> search(Filter filter);
}
