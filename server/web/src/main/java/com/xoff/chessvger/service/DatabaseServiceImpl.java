package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.repository.DatabaseRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseServiceImpl implements IDatabaseService {


  @Autowired
  private DatabaseRepository databaseRepository;
  public Long count() {
    return databaseRepository.count();
  }
  public List<DatabaseEntity> findAll(){
    org.springframework.data.domain.Page<DatabaseEntity> page=databaseRepository.findAll(
        org.springframework.data.domain.Pageable.ofSize(5));
    return  page.stream().toList();
  }

}
