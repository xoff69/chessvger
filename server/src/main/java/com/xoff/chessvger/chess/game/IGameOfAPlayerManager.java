package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import java.util.List;

public interface IGameOfAPlayerManager {

  public void clear();

  public List<CommonGame> listGameOfAPlayer(DatabaseManager databaseManager, long idPlayer);

  public int countGameOfAPlayer(Long idPlayer);


  public void finish();

  public void ajoute(long item, long idgame);
}
