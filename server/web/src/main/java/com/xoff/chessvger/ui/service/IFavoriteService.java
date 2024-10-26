package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.view.PageView;

public interface IFavoriteService {
  public PageView<CommonGame> managePage(Pageable paging, long userId);

  public boolean updateFavorite(long bdId, long gameId, long userId);
}
