package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.view.PageView;

public interface IHistoryService {
  public PageView<CommonGame> managePage(Pageable paging, long bdId);
}
