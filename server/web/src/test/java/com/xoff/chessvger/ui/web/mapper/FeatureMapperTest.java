package com.xoff.chessvger.ui.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.chess.feature.Feature;
import com.xoff.chessvger.view.FeatureDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("IT")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeatureMapperTest {

  @Autowired
  FeatureMapper mapper;

  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
    Feature feature = new Feature();
    feature.setName("SourceName");
    feature.setId(1L);

    FeatureDto featureDto = mapper.entity2Dto(feature);

    assertEquals(feature.getName(), featureDto.getName());
    assertEquals(feature.getId(), featureDto.getId());
  }


}