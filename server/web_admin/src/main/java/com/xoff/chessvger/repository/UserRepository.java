package com.xoff.chessvger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface  UserRepository  extends CrudRepository<UserEntity, Long> {
  Page<UserEntity> findAll(Pageable pageable);
  UserEntity findByLoginAndPassword(String login, String password);
}
