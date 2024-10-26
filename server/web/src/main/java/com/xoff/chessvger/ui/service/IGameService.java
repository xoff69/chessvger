package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.view.CoupleFenMoveId;
import com.xoff.chessvger.ui.web.view.CoupleLongView;
import com.xoff.chessvger.ui.web.view.PageView;

public interface IGameService {

  public PageView<CommonGame> managePage(Pageable paging, long bdId);

  public PageView<CommonGame> managePage(Pageable paging, long bdId, Filter filter);

  public void gameOpen(long bdId, long gameId);
  public boolean gameFlip(long bdId, long gameId);
  public CoupleLongView gameClose(long bdId, long gameId);

  public CoupleFenMoveId gameGetFenNavigation(Long bdId, Long gameId, int moveId, int where);
}
