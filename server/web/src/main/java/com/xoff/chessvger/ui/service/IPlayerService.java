package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import java.util.List;

public interface IPlayerService {
  public PageView managePage(Pageable paging, long bdId);
}
