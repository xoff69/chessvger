package com.xoff.chessvger.chess.feature;

import com.xoff.chessvger.common.ICommonManager;

public interface IFeatureManager extends ICommonManager<Long, Feature> {
  public Feature findByName(String name);

}
