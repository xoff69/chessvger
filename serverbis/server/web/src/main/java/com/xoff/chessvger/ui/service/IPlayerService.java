package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.repository.CommonPlayerEntity;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import java.util.List;

public interface IPlayerService {
  public List<CommonPlayerEntity> findAll();
  public PageView managePage(Pageable paging, long bdId);
}
