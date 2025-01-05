package com.xoff.chessvger.ui.service;


import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.CoupleLongView;
import com.xoff.chessvger.view.PageView;

public interface IDatabaseService {

  public PageView managePage(Pageable paging, long userId);

  public void createBD(DBForm dbForm);

  public void dbOpen(long bdId);

  public CoupleLongView dbClose(long bdId);
}
