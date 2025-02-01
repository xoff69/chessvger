package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.repository.DatabaseEntity;
import java.util.List;

public interface IDatabaseService {

  List<DatabaseEntity> findAll();
  Long count();


}
