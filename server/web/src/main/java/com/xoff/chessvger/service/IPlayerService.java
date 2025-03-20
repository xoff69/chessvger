package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.CommonPlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPlayerService {
     Page<CommonPlayerEntity> findAll(Pageable pageable);
   Long count();
  CommonPlayerEntity findById(Long id);
}
