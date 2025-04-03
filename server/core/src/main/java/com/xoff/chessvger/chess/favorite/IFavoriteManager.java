package com.xoff.chessvger.chess.favorite;

import com.xoff.chessvger.model.CommonGame;
import com.xoff.chessvger.model.Favorite;

import java.util.List;

public interface IFavoriteManager {


    List<CommonGame> listFavorite(long userId);

    void clear();

    void add(long key, Favorite value);


    void finish();

    Favorite get(long key);
}
