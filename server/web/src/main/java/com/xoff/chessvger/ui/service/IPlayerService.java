package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.view.PageView;

public interface IPlayerService {

  public PageView managePage(Pageable paging, long bdId);
}
