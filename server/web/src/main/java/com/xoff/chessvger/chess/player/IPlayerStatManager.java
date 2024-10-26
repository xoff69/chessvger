package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.ui.web.view.StatJoueurView;

public interface IPlayerStatManager {
  public StatJoueurView getStatJoueur(DatabaseManager databaseManager, CommonPlayer player);
}
