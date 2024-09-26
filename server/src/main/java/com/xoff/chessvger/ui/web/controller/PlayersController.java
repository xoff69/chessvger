package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.repository.ImportePlayerBatch;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.IPlayerService;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.ui.web.view.PageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class PlayersController {

  @Autowired
  Navigation navigation;
  @Autowired
  IPlayerService iPlayerService;

  @Autowired
  ImportePlayerBatch importePlayerBatch;

  @GetMapping("/load")
  public ResponseEntity<String> importe(){
    long c=importePlayerBatch.importeFidePlayer();
    return new ResponseEntity<>("ok "+c,
        HttpStatus.OK);
  }

  @GetMapping("/players")
  public ResponseEntity<PageView> getAllItems(@RequestParam(name = "bdId") long bdId,
                                              @RequestParam(defaultValue = "0", name = "page")
                                              int page,
                                              @RequestParam(defaultValue = "10", name = "size")
                                              int size) {
    log.info("chargement player");
    return new ResponseEntity<>(iPlayerService.managePage(PageRequest.of(page, size), bdId),
        HttpStatus.OK);

  }
}
