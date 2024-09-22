package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.ui.web.view.CoupleLongView;
import com.xoff.chessvger.ui.web.view.PageView;

public interface IDatabaseService {

  public PageView managePage(Pageable paging, long userId);

  public void createBD(DBForm dbForm);

  public void dbOpen(long bdId);

  public CoupleLongView dbClose(long bdId);
}
