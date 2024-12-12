package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import java.util.List;

public interface IGameOfAPlayerManager {

  void clear();

  List<CommonGame> listGameOfAPlayer(DatabaseManager databaseManager, long idPlayer);

  int countGameOfAPlayer(Long idPlayer);


  void finish();

  void ajoute(long item, long idgame);
}
