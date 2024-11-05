package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.IFavoriteService;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class FavoriteController {
  @Autowired
  IFavoriteService iFavoriteService;

  @GetMapping("/gamesFavorite")
  public ResponseEntity<PageView> getAllGames(@RequestParam(name = "userId") long userId,
                                              @RequestParam(defaultValue = "0", name = "page")
                                              int page,
                                              @RequestParam(defaultValue = "10", name = "size")
                                              int size) {
    Pageable paging = PageRequest.of(page, size);

    return ResponseEntity.ok(iFavoriteService.managePage(paging, userId));

  }

  @PatchMapping(value = "/favorite/updateFavorite/{bdId}/{gameId}/{userId}")
  public ResponseEntity<String> addFavorite(@PathVariable(name = "bdId") Long bdId,
                                            @PathVariable(name = "gameId") Long gameId,
                                            @PathVariable(name = "userId") Long userId) {
    iFavoriteService.updateFavorite(bdId, gameId, userId);
    // TODO pas ouf
    return ResponseEntity.ok("ok");
  }
}
