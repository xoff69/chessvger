package com.xoff.chessvger.chess.favorite;

import com.xoff.chessvger.chess.game.CommonGame;
import java.util.List;

public interface IFavoriteManager {


  List<CommonGame> listFavorite(long userId);

  void clear();

  void add(long key, Favorite value);


  void finish();

  Favorite get(long key);
}
