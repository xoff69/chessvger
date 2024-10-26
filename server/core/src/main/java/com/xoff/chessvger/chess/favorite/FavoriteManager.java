package com.xoff.chessvger.chess.favorite;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FavoriteManager implements IFavoriteManager {

  private final FavoriteMap map;

  public FavoriteManager() {
    map = new FavoriteMap(ParamConstants.DATA_FOLDER_COMMON + "FavoriteMap" + Constants.MAP_SFX);


  }

  public List<CommonGame> listFavorite(long userId) {
    List<CommonGame> games = new ArrayList<>();
    List<Favorite> favorites = map.list();
    log.info("favorte game listFavorite" + favorites.size());

    for (Favorite favorite : favorites) {
      if (favorite.getIdUser() == userId) {
        long bdId = favorite.getBdId();
        long gameID = favorite.getIdGame();
        Database database = GlobalManager.getInstance().getDatabaseMap().get(bdId);
        DatabaseManager databaseManager = new DatabaseManager(database);
        CommonGame game = databaseManager.getGameById(gameID);
        games.add(game);
        log.info("favorte game find " + game);
      }
    }
    return games;

  }

  public void clear() {
    map.clear();
  }

  public void add(long key, Favorite value) {
    map.add(key, value);
  }


  public void finish() {
    map.commit();
  }

  public Favorite get(long key) {
    return map.get(key);
  }
}
