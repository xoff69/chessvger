package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ui.service.IBrowseService;
import com.xoff.chessvger.view.StatBrowserView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class BrowseController {

  @Autowired
  IBrowseService iBrowseService;

  @GetMapping("/browses")
  public ResponseEntity<List<StatBrowserView>> getAllItems(@RequestParam(name = "bdId") long bdId) {
    return ResponseEntity.ok(iBrowseService.loadAll(bdId));

  }

  @GetMapping("/browseOne")
  public ResponseEntity<List<StatBrowserView>> browseOne(@RequestParam(name = "bdId") long bdId,
                                                         @RequestParam(name = "browseId")
                                                         long browseId) {
    // TODO
    log.info("browseOne");
    return ResponseEntity.ok(iBrowseService.loadAll(bdId));

  }
}
