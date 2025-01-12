package com.xoff.chessvger.ui.web.controller.tomigrate;

import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.IHistoryService;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class HistoryController {
  @Autowired
  IHistoryService iHistoryService;

  @GetMapping("/gamesHistory")
  public ResponseEntity<PageView> getAllGames(@RequestParam(name = "bdId") long bdId,
                                              @RequestParam(defaultValue = "0", name = "page")
                                              int page,
                                              @RequestParam(defaultValue = "10", name = "size")
                                              int size) {
    Pageable paging = PageRequest.of(page, size);

    return ResponseEntity.ok(iHistoryService.managePage(paging, bdId));

  }
}
