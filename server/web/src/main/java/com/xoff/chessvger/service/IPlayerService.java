package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.CommonPlayerEntity;
import java.util.List;

public interface IPlayerService {
   List<CommonPlayerEntity> findAll();
   Long count();
  CommonPlayerEntity findById(Long id);
}
