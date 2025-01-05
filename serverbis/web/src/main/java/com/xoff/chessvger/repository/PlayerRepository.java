package com.xoff.chessvger.repository;

import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface  PlayerRepository  extends CrudRepository<CommonPlayerEntity, Long> {

  Page<CommonPlayerEntity> findAll(Pageable pageable);
  CommonPlayerEntity findById(long id);

}