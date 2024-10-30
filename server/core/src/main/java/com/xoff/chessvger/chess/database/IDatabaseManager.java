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

  public long getDatabaseId();

  public String getDatabaseName();
  public IPlayerOfDbManager getPlayerOfDbManager();

  public void finish();

  public long duplicate(long userId);

  public void clear();

  public String createName();

  public boolean parseMoves2(CommonGame game);

  public String exportePgn();

  public int importePgn(String emplacement);

  public List<CommonGame> search(Filter filter);


  public CommonGame getGameById(long id);


  public List<JoueurView> getPlayersWithGames(String param, Pageable paging);

  public void postUpdateGameAndStat(CommonGame game);


  public int postUpdateGameAndStat();


  public long getLastUpdate();


  public CommonGame upsert(CommonGame item, DBOperation operation);

  public boolean optimiser(boolean withDeleted, boolean deleteDoublon);

  public void delete();


  public List<StatBrowserView> getBrowseData(List<String> movesAlreadyPlayed);


  public StatJoueurView getStatJoueur(CommonPlayer player);


  public StatGame getStatGame(List<CommonGame> games);

  public IGlobalGameManager getGlobalGameManager();

  public IPositionManager getPositionManager();


  public IMaterialManager getMaterialManager();

  public IGlobalBrowserStatManager getGlobalBrowserStatManager();


  public IPlayerStatManager getPlayerStatManager();

  public IGameStatManager getGameStatManager();

  public IGameWhereMapManager getGameWhereMapManager();

  public IGameOfAPlayerManager getGameOfAPlayerManager();


  public IHistoryManager getHistoryManager();

}
