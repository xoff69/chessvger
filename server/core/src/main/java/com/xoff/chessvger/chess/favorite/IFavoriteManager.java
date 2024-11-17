package com.xoff.chessvger.chess.favorite;

import com.xoff.chessvger.chess.game.CommonGame;
import java.util.List;

public interface IFavoriteManager {


  public List<CommonGame> listFavorite(long userId);

  public void clear();

  public void add(long key, Favorite value);


  public void finish();

  public Favorite get(long key);
}
