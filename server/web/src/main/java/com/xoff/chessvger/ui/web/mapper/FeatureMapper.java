package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.feature.Feature;
import com.xoff.chessvger.view.FeatureDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class FeatureMapper {

  @Named("mapChecked")
  static boolean mapChecked(String enabled) {
    return "on".equals(enabled) || "true".equals(enabled);
  }


  public abstract FeatureDto entity2Dto(Feature feature);


  public abstract List<Feature> map(List<FeatureDto> dtos);

  @Mapping(source = "enabled", target = "enabled", qualifiedByName = "mapChecked")
  public abstract Feature dto2entity(FeatureDto dto);

  public abstract List<FeatureDto> mapListEntity2Dto(List<Feature> features);
}
