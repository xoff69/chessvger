package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.repertoire.Repertoire;
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.repository.CommonGameEntity;
import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.repository.DatabaseRepository;
import com.xoff.chessvger.repository.GameRepository;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.CoupleLongView;
import com.xoff.chessvger.view.PageView;
import java.util.ArrayList;
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
