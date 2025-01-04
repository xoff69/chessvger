package com.xoff.chessvger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<CommonGameEntity, Long> {

  Page<CommonGameEntity> findAll(Pageable pageable);
  CommonGameEntity findById(long id);

}