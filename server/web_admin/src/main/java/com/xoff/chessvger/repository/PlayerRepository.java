package com.xoff.chessvger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface  PlayerRepository  extends CrudRepository<CommonPlayerEntity, Long> {

  Page<CommonPlayerEntity> findAll(Pageable pageable);
  CommonPlayerEntity findById(long id);

}