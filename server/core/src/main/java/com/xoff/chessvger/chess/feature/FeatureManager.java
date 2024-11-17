package com.xoff.chessvger.chess.feature;

import com.xoff.chessvger.common.ACommonManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeatureManager extends ACommonManager<Long, Feature> implements IFeatureManager {


  public FeatureManager() {
    super(ParamConstants.DATA_FOLDER_COMMON + "FeatureMap" + Constants.MAP_SFX);
  }

  public Feature findByName(String name) {
    List<Feature> features = findAll();
    for (Feature feature : features) {
      if (feature.getName().equals(name)) {
        return feature;
      }
    }
    return null;
  }

}
