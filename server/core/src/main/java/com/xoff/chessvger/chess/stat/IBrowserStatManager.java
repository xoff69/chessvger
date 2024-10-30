package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.view.StatBrowserView;
import java.util.List;

public interface IBrowserStatManager {

  public List<CommonGame> getGames(Position p);


  public void clear();


  public void finish();

  public void browseFirstMove(List<CommonGame> liste);

  public List<StatBrowserView> getBrowseData(DatabaseManager databaseManager,
                                             List<String> pastMoves);
}
