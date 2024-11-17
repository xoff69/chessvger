package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.util.Constants;


public class GameWhereMap extends AdbCommonKeyLong<String> {


  public GameWhereMap(String dbName) {
    super(DatabaseManager.getFolder(dbName) + dbName + "GameWhereMap" + Constants.MAP_SFX);

  }


}
