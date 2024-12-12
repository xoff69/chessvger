package com.xoff.chessvger.chess.database;

import com.xoff.chessvger.chess.board.IMaterialManager;
import com.xoff.chessvger.chess.board.IPositionManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.IGameOfAPlayerManager;
import com.xoff.chessvger.chess.game.IGameStatManager;
import com.xoff.chessvger.chess.game.IGameWhereMapManager;
import com.xoff.chessvger.chess.game.IGlobalGameManager;
import com.xoff.chessvger.chess.history.IHistoryManager;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.chess.player.IPlayerOfDbManager;
import com.xoff.chessvger.chess.player.IPlayerStatManager;
import com.xoff.chessvger.chess.stat.IGlobalBrowserStatManager;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.JoueurView;
import com.xoff.chessvger.view.StatBrowserView;
import com.xoff.chessvger.view.StatGame;
import com.xoff.chessvger.view.StatJoueurView;
import java.util.List;

public interface IDatabaseManager {

  long getDatabaseId();

  String getDatabaseName();

  IPlayerOfDbManager getPlayerOfDbManager();

  void finish();

  long duplicate(long userId);

  void clear();

  String createName();

  boolean parseMoves2(CommonGame game);

  String exportePgn();

  int importePgn(String emplacement);

  List<CommonGame> search(Filter filter);


  CommonGame getGameById(long id);


  List<JoueurView> getPlayersWithGames(String param, Pageable paging);

  void postUpdateGameAndStat(CommonGame game);


  int postUpdateGameAndStat();


  long getLastUpdate();


  CommonGame upsert(CommonGame item, DBOperation operation);

  boolean optimiser(boolean withDeleted, boolean deleteDoublon);

  void delete();


  List<StatBrowserView> getBrowseData(List<String> movesAlreadyPlayed);


  StatJoueurView getStatJoueur(CommonPlayer player);


  StatGame getStatGame(List<CommonGame> games);

  IGlobalGameManager getGlobalGameManager();

  IPositionManager getPositionManager();


  IMaterialManager getMaterialManager();

  IGlobalBrowserStatManager getGlobalBrowserStatManager();


  IPlayerStatManager getPlayerStatManager();

  IGameStatManager getGameStatManager();

  IGameWhereMapManager getGameWhereMapManager();

  IGameOfAPlayerManager getGameOfAPlayerManager();


  IHistoryManager getHistoryManager();

}
