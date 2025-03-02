package com.xoff.chessvger.service;

import com.xoff.chessvger.model.DatabaseModel;

import java.util.List;

public interface IDatabaseService {

  List<DatabaseModel> findAll();
  Long count();
  public DatabaseModel findById(Long id);

}
