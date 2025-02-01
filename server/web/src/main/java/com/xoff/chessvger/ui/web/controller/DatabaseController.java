package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.ui.ResponseList;
import com.xoff.chessvger.ui.service.IDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class DatabaseController {

  @Autowired
  IDatabaseService iDatabaseService;



  @GetMapping("/api/databases/all")
  public ResponseEntity<ResponseList<DatabaseEntity>> all(@RequestParam long userId){

    System.out.println("Reçu  userId: " + userId);

    return new ResponseEntity<>(new ResponseList(iDatabaseService.findAll(),iDatabaseService.count()),
        HttpStatus.OK);
  }



}
