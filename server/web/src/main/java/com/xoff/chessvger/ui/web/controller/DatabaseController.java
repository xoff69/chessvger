package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.service.DatabaseHelperService;
import com.xoff.chessvger.service.UserService;
import com.xoff.chessvger.ui.JwtUtil;
import com.xoff.chessvger.ui.ResponseList;
import com.xoff.chessvger.service.IDatabaseService;
import com.xoff.chessvger.ui.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DatabaseController {

  @Autowired
  IDatabaseService iDatabaseService;
  @Autowired
  private UserService userService;

  @Autowired
  DatabaseHelperService databaseHelperService;


  @GetMapping("/api/databases/all")
  public ResponseEntity<ResponseList<DatabaseEntity>> all(@RequestHeader ("Authorization") String token){


    databaseHelperService.setDatasource(token,"common");
    return new ResponseEntity<>(new ResponseList(iDatabaseService.findAll(),iDatabaseService.count()),
        HttpStatus.OK);
  }



}
