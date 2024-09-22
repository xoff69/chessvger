package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.exception.CrudException;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.PackService;
import com.xoff.chessvger.ui.service.UserPackService;
import com.xoff.chessvger.ui.service.UserService;
import com.xoff.chessvger.ui.web.form.UserPackForm;
import com.xoff.chessvger.ui.web.mapper.UserPackMapper;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.ui.web.view.PageView;
import com.xoff.chessvger.ui.web.view.UserPackDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@Slf4j
public class PackController {
  @Autowired
  PackService packService;
  @Autowired
  UserPackService userPackService;

  @Autowired
  UserService userService;

  @Autowired
  Navigation navigation;
  @Autowired
  private UserPackMapper userPackMapper;


  @GetMapping("/packall")
  public ResponseEntity<List<Pack>> packAll() {

    return ResponseEntity.ok(packService.list());

  }

  @GetMapping("/mypacks")
  public ResponseEntity<PageView> getMyPack(
      @RequestParam(defaultValue = "0", name = "page") int page,
      @RequestParam(defaultValue = "10", name = "size") int size) {


    return ResponseEntity.ok(
        packService.managePage(PageRequest.of(page, size), navigation.getUserDto().getId()));

  }

  @GetMapping("/adminpacks")
  public ResponseEntity<PageView> getAdminpack(
      @RequestParam(defaultValue = "0", name = "page") int page,
      @RequestParam(defaultValue = "10", name = "size") int size) {
    return ResponseEntity.ok(packService.managePage(PageRequest.of(page, size)));

  }

  @GetMapping("/adminuserspacks")
  public ResponseEntity<PageView> getAdminuserpack(
      @RequestParam(defaultValue = "0", name = "page") int page,
      @RequestParam(defaultValue = "10", name = "size") int size) {

    return ResponseEntity.ok(packService.managePageUserPack(PageRequest.of(page, size)));

  }

  @PostMapping("/userpack")
  public ResponseEntity<String> create(@RequestBody UserPackForm form) {
    log.info("dto " + form);
    // il faut que le user et le pack existe et que le couple n existe pas deja
    try{
      UserPackDto dtoSaved = userPackMapper.entity2Dto(userPackService.create(form));
      String msg=dtoSaved.toString();
      log.info("UserPackDto  saved " + dtoSaved);
      return  ResponseEntity.ok(msg);
    }
    catch (CrudException crud){
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "User pack creadtion erro", crud);
    }


  }
}
