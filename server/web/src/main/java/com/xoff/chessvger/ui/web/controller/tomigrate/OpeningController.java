package com.xoff.chessvger.ui.web.controller.tomigrate;

import com.xoff.chessvger.ui.service.IOpeningService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class OpeningController {

  @Autowired
  private IOpeningService iOpeningService;

  @GetMapping("/openings")
  public ResponseEntity<List> getAll() {

    return ResponseEntity.ok(iOpeningService.getAll());

  }
}
