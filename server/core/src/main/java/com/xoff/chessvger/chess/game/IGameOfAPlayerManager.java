package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.model.CommonGame;

import java.util.List;

public interface IGameOfAPlayerManager {

    void clear();

    List<CommonGame> listGameOfAPlayer(DatabaseManager databaseManager, long idPlayer);

    int countGameOfAPlayer(Long idPlayer);


    void finish();

    void ajoute(long item, long idgame);
}
