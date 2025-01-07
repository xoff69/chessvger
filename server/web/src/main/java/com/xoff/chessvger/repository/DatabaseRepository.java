package com.xoff.chessvger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DatabaseRepository extends CrudRepository<DatabaseEntity, Long> {

  Page<DatabaseEntity> findAll(Pageable pageable);
  DatabaseEntity findById(long id);

}