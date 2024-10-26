package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.feature.Feature;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.mapper.FeatureMapper;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.ui.web.view.FeatureDto;
import com.xoff.chessvger.ui.web.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeatureServiceImpl implements FeatureService {


  @Autowired
  private FeatureMapper featureMapper;



  @Override
  public void deleteAll() {
    GlobalManager.getInstance().getFeatureManager().clear();
  }


  @Override
  public List<FeatureDto> saveAll(List<FeatureDto> list) {
    return featureMapper.mapListEntity2Dto(
        GlobalManager.getInstance().getFeatureManager().saveAll(featureMapper.map(list)));
  }

  @Override
  public FeatureDto findById(Long id) {
    Feature found = GlobalManager.getInstance().getFeatureManager().get(id);
    if (found == null) {
      throw new ServiceException("Feature not found");
    } else {
      return featureMapper.entity2Dto(found);
    }
  }

  @Override
  public boolean delete(Long id) {

    FeatureDto dto = findById(id);
    if (dto != null) {
      GlobalManager.getInstance().getFeatureManager().deleteById(id);
      return true;
    } else {
      return false;
    }

  }

  @Override
  public FeatureDto create(FeatureDto dto) {
    Feature feature = featureMapper.dto2entity(dto);
    log.info(feature.toString());
    feature = GlobalManager.getInstance().getFeatureManager().create(feature);
    return featureMapper.entity2Dto(feature);
  }

  @Override
  public FeatureDto update(Long id, FeatureDto dto) {
    FeatureDto toUpdate = findById(id);
    if (toUpdate == null) {

      throw new ServiceException("Feature not found");
    }
    Feature feature = featureMapper.dto2entity(dto);
    feature.setId(id);
    GlobalManager.getInstance().getFeatureManager().update(feature);
    return featureMapper.entity2Dto(feature);

  }

  public PageView<FeatureDto> findAll(Pageable paging) {
    List<Feature> items = GlobalManager.getInstance().getFeatureManager().findAll();
    return Page.compute(paging, featureMapper.mapListEntity2Dto(items));
  }

  public List<FeatureDto> getAll() {
    return featureMapper.mapListEntity2Dto(
        GlobalManager.getInstance().getFeatureManager().findAll());
  }
}
