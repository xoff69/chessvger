package com.xoff.chessvger.service;
import com.xoff.chessvger.model.CommonPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPlayerService {
     Page<CommonPlayer> findAll(Pageable pageable);
   Long count();
  CommonPlayer findById(Long id);
}
