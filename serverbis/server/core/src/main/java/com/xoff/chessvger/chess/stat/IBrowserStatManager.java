package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.view.StatBrowserView;
import java.util.List;

public interface IBrowserStatManager {

  List<CommonGame> getGames(Position p);


  void clear();


  void finish();

  void browseFirstMove(List<CommonGame> liste);

  List<StatBrowserView> getBrowseData(DatabaseManager databaseManager, List<String> pastMoves);
}
