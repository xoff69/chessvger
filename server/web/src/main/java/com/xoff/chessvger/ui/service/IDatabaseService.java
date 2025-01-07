package com.xoff.chessvger.ui.service;


import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.repository.CommonGameEntity;
import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.CoupleLongView;
import com.xoff.chessvger.view.PageView;
import java.util.List;

public interface IDatabaseService {

  public List<DatabaseEntity> findAll();
  public Long count();


  public PageView managePage(Pageable paging, long userId);

  public void createBD(DBForm dbForm);

  public void dbOpen(long bdId);

  public CoupleLongView dbClose(long bdId);
}
