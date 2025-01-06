package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.view.StatJoueurView;

public interface IPlayerStatManager {
  StatJoueurView getStatJoueur(DatabaseManager databaseManager, CommonPlayer player);
}
