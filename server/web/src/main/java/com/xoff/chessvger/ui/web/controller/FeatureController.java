package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.FeatureService;
import com.xoff.chessvger.ui.web.navigation.ApplicationBean;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.FeatureDto;
import com.xoff.chessvger.view.PageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FeatureController {

  @Autowired
  private FeatureService featureService;

 // @Autowired
  //private ApplicationBean applicationBean;

  @GetMapping(path = "/features")
  public ResponseEntity<PageView> getAll(@RequestParam(defaultValue = "0", name = "page") int page,
                                         @RequestParam(defaultValue = "1000", name = "size")
                                         int size) {

    Pageable paging = PageRequest.of(page, size);
    return new ResponseEntity<>(featureService.findAll(paging), HttpStatus.OK);

  }


  @PatchMapping(value = "/features/{id}")
  public ResponseEntity<String> updateEnabled(@PathVariable(name = "id") Long id) {
    log.info(" feature patch " + id);
    FeatureDto dtoUpdated = featureService.findById(id);
    log.info(" feature patch " + dtoUpdated);
    if (Boolean.parseBoolean(dtoUpdated.getEnabled())) {
      dtoUpdated.setEnabled("false");
    } else {
      dtoUpdated.setEnabled("true");
    }
    featureService.update(id, dtoUpdated);
   // applicationBean.reset();
    log.info(" feature patch " + dtoUpdated);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
  }

  @PutMapping(value = "/features/{id}")
  public ResponseEntity<FeatureDto> update(@RequestBody FeatureDto dto,
                                           @PathVariable(name = "id") Long id) {
    FeatureDto dtoUpdated = featureService.update(id, dto);
  //  applicationBean.reset();
    if (dtoUpdated != null) {
      return new ResponseEntity<>(dtoUpdated, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new FeatureDto(), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(value = "/features/{id}")
  public ResponseEntity<FeatureDto> findById(@PathVariable(name = "id") Long id) {
    log.info("findById feature  " + id);
    FeatureDto dtoFound = featureService.findById(id);
    if (dtoFound != null) {
      return new ResponseEntity<FeatureDto>(dtoFound, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
