package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.FeatureDto;
import com.xoff.chessvger.view.PageView;
import java.util.List;

public interface FeatureService {
  FeatureDto create(FeatureDto dto);

  void deleteAll();

  FeatureDto update(Long id, FeatureDto dto);

  PageView<FeatureDto> findAll(Pageable paging);

  List<FeatureDto> saveAll(List<FeatureDto> list);

  List<FeatureDto> getAll();

  FeatureDto findById(Long id);

  boolean delete(Long id);

}
