package com.xoff.chessvger.admin.ui.service;

import com.xoff.chessvger.admin.repository.CommonPlayerEntity;
import java.util.List;

public interface IPlayerService {
  public List<CommonPlayerEntity> findAll();
  public Long count();
}
