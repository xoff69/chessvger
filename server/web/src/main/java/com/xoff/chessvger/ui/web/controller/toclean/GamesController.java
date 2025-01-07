package com.xoff.chessvger.ui.web.controller.toclean;

import com.xoff.chessvger.repository.CommonGameEntity;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.IGameService;
import com.xoff.chessvger.ui.web.form.FilterForm;
import com.xoff.chessvger.ui.web.mapper.FilterMapper;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.CoupleFenMoveId;
import com.xoff.chessvger.view.CoupleLongView;
import com.xoff.chessvger.view.GameView;
import com.xoff.chessvger.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class GamesController {
  @Autowired
  Navigation navigation;


  @Autowired
  FilterMapper filterMapper;

  @Autowired
  IGameService iGameService;

  @PostMapping( path ="/searchGame", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseEntity<PageView> searchGame(@RequestBody FilterForm filterForm,
                                                           @RequestParam(defaultValue = "0", name = "page")
                                                           int page,
                                                           @RequestParam(defaultValue = "10", name = "size")
                                                           int size) {

    log.info("search bdid = " + filterForm.getBdId());
    log.info(filterForm.toString());
    log.info("search bdid = " + filterForm.getBdId());
    navigation.getBdFilters().put(filterForm.getBdId(), filterForm);
    // TODO voir s il y a un filterForm
    // TODO faire les verifs
    // FIXME les pages: on fait quoi sur la pagination apres une recherche ?
    //  toujours s appuyer sur le filterForm
    //@FIXME
    filterForm.setCasGeneral(true);
    Pageable paging = PageRequest.of(page, size);

    return ResponseEntity.ok(iGameService.managePage(paging, filterForm.getBdId(),
        filterMapper.form2entity(filterForm)));
  }
  @GetMapping("/api/games/count")
  public ResponseEntity<Long> count(){
    return new ResponseEntity<>(iGameService.count(),
        HttpStatus.OK);
  }

  @GetMapping("/api/games/all")
  public ResponseEntity<List<CommonGameEntity>> all(){
    return new ResponseEntity<>(iGameService.findAll(),
        HttpStatus.OK);
  }
  @GetMapping("/games")
  public ResponseEntity<PageView> getAllGames(@RequestParam(name = "bdId") long bdId,
                                              @RequestParam(defaultValue = "1", name = "page")
                                              int page,
                                              @RequestParam(defaultValue = "10", name = "size")
                                              int size) {

    log.info("getAllGames taille =" + size + " " + page);
    Pageable paging = PageRequest.of(page, size);
    return ResponseEntity.ok(iGameService.managePage(paging, bdId));

  }


  @GetMapping("/gameOpen")
  public String gameOpen(@RequestParam(name = "bdId") long bdId,
                         @RequestParam(name = "gameId") long gameId) {
    log.info(gameId+" -----------gameOpen open " + bdId);
    iGameService.gameOpen(bdId, gameId);

    return "redirect:/";
  }

  @GetMapping("/game")
  public ResponseEntity<GameView> game(@RequestParam(name = "bdId") long bdId,
                                       @RequestParam(name = "gameId") long gameId) {
    log.info("appel game:::" + bdId + ":" + gameId);

    GameView gv = navigation.getCacheGameView().get(bdId + "_" + gameId);
    gv.setGameId(gameId);
    gv.setBdId(bdId);
    gv.computeHtml();
    return ResponseEntity.ok(gv);
  }
  @PatchMapping(value = "/game/flip/{bdId}/{gameId}")
  public ResponseEntity<Boolean> gameFlip(@PathVariable(name = "bdId") Long bdId,
                                                  @PathVariable(name = "gameId") Long gameId) {

       log.info("game flip");
    return ResponseEntity.ok(iGameService.gameFlip(bdId, gameId));
  }
  @PatchMapping(value = "/game/close/{bdId}/{gameId}")
  public ResponseEntity<CoupleLongView> gameClose(@PathVariable(name = "bdId") Long bdId,
                                                  @PathVariable(name = "gameId") Long gameId) {

    log.info("game close");
    return ResponseEntity.ok(iGameService.gameClose(bdId, gameId));
  }

  @PatchMapping(value = "/gameGetFenNavigation/{bdId}/{gameId}/{moveId}/{where}")
  public ResponseEntity<CoupleFenMoveId> gameGetFenNavigation(
      @PathVariable(name = "bdId") Long bdId, @PathVariable(name = "gameId") Long gameId,
      @PathVariable(name = "moveId") int moveId, @PathVariable(name = "where") int where) {
    log.info("gameGetFenNavigation close");
    return ResponseEntity.ok(iGameService.gameGetFenNavigation(bdId, gameId, moveId, where));
  }
}
