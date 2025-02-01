package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.CommonPlayerEntity;
import java.util.List;

public interface IPlayerService {
  public List<CommonPlayerEntity> findAll();
  public Long count();
}
