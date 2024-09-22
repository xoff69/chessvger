package com.xoff.chessvger.ui.web.navigation;

import com.xoff.chessvger.ui.service.FeatureService;
import com.xoff.chessvger.ui.web.view.FeatureDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("applicationBean")

@Slf4j
public class ApplicationBean {
  @Autowired
  private FeatureService featureService;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  @Getter
  private List<FeatureDto> featureDtos;
  @Value("${chessvger.version}")
  @Getter
  private String version;
  @Value("${chessvger.contact}")
  @Getter
  private String contact;


  @PostConstruct
  public void init() {

    reset();
    log.info("init ApplicatiobBean " + featureDtos.size());
  }

  public void reset() {
    featureDtos = (featureService.getAll());
  }

  public boolean isAllowedFeature(String name) {
    log.info("ApplicatiobBean isAllowedFeature " + name);
    for (FeatureDto featureDto : featureDtos) {
      if (featureDto.getName().equals(name)) {
        log.info("ApplicatiobBean isAllowedFeature " + featureDto);
        return Boolean.parseBoolean(featureDto.getEnabled());
      }
    }
    return false;
  }
}
