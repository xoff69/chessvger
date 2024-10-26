package com.xoff.chessvger.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
public interface  PlayerRepository  extends CrudRepository<CommonPlayerEntity, Long> {


  CommonPlayerEntity findById(long id);
}