package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.repository.CommonPlayerEntity;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.IPlayerService;
import com.xoff.chessvger.view.PageView;
import java.util.List;
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
  IPlayerService iPlayerService;

  @GetMapping("/api/admin/players/all")
  public ResponseEntity<List<CommonPlayerEntity>> all(){
    return new ResponseEntity<>(iPlayerService.findAll(),
        HttpStatus.OK);
  }
  @GetMapping("/api/admin/players/count")
  public ResponseEntity<Long> count(){
    return new ResponseEntity<>(iPlayerService.count(),
        HttpStatus.OK);
  }

}
