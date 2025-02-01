package com.xoff.chessvger.ui.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.repository.CommonGameEntity;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.ui.ResponseList;
import com.xoff.chessvger.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class GamesController {





  @Autowired
  GameService gameService;

  @Autowired
  RedisMessagePublisher redisMessagePublisher;


  @GetMapping("/api/games/all")
  public ResponseEntity<ResponseList<CommonGameEntity>> all(){

    return new ResponseEntity<>(new ResponseList(gameService.handleAllGames(),gameService.count()),
        HttpStatus.OK);
  }
  @GetMapping("/api/games/findById")
  public ResponseEntity<CommonGameEntity>


  findById(@RequestParam("id") Long id){
    return new ResponseEntity<>(gameService.findById(id),
        HttpStatus.OK);
  }
  @GetMapping("/api/games/all2")
  public ResponseEntity<ResponseList<CommonGameEntity>> all2(){

    return new ResponseEntity<>(new ResponseList(gameService.handleAllGames(),gameService.count()),
        HttpStatus.OK);
  }
  @PostMapping("/api/games/import")
  public ResponseEntity<String> importPgn(@RequestBody ApiRequest request)
      throws JsonProcessingException {
    String databaseId = request.getDatabaseId();
    String userId = request.getUserId();
    System.out.println("Reçu databaseId: " + databaseId + ", userId: " + userId);
    // TODO appeler redis pour avoir le tenantId?
    long tenantId=1;

    MessageToParser messageGame=new MessageToParser();
    messageGame.setTenantId(tenantId);
    messageGame.setFolderToParse("./data/big");
    messageGame.setDatabaseName("chessvger_admin_database");
    messageGame.setSchema("main");  // TODO renommer
    messageGame.setActionQueue(ActionQueue.PARSEGAME);

    ObjectMapper objectMapper=new ObjectMapper();

    redisMessagePublisher.publish(objectMapper.writeValueAsString(messageGame));

    return ResponseEntity.ok("Requête traitée avec succès pour userId: " + tenantId);
  }

}
