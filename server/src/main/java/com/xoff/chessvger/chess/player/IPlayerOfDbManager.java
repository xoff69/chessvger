package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import java.util.List;

public interface IPlayerOfDbManager {
  public List<Long> listIdsOfPlayer();

  public void clear();


  public void finish();

  public void add( long idplayer);
}
