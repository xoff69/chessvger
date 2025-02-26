package com.xoff.chessvger.ui.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.repository.CommonGameEntity;
import com.xoff.chessvger.repository.TenantEntity;
import com.xoff.chessvger.service.DatabaseHelperService;
import com.xoff.chessvger.service.TenantService;
import com.xoff.chessvger.service.UserService;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.ui.JwtUtil;
import com.xoff.chessvger.ui.ResponseList;
import com.xoff.chessvger.service.GameService;
import com.xoff.chessvger.ui.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
public class GamesController {


  @Autowired
  GameService gameService;

  @Autowired
  RedisMessagePublisher redisMessagePublisher;

  @Autowired
  DatabaseHelperService databaseHelperService;

  @GetMapping("/api/games/all")
  public ResponseEntity<ResponseList<CommonGameEntity>> all(@RequestHeader ("Authorization") String token){

  // TODO trouver le nom a partir du bd id
    databaseHelperService.setDatasource(token,"main");
    return new ResponseEntity<>(new ResponseList(gameService.handleAllGames(),gameService.count()),
        HttpStatus.OK);
  }
  @GetMapping("/api/games/findById")
  public ResponseEntity<CommonGameEntity>
  findById(@RequestHeader ("Authorization") String token,@RequestParam("id") Long id){


    // TODO trouver le nom a partir du bd id
    databaseHelperService.setDatasource(token,"main");

    return new ResponseEntity<>(gameService.findById(id),
        HttpStatus.OK);
  }
  @GetMapping("/api/games/all2")
  public ResponseEntity<ResponseList<CommonGameEntity>> all2(@RequestHeader ("Authorization") String token){

    // TODO trouver le nom a partir du bd id
    databaseHelperService.setDatasource(token,"main");
    return new ResponseEntity<>(new ResponseList(gameService.handleAllGames(),gameService.count()),
        HttpStatus.OK);
  }
  @PostMapping("/api/games/import")
  public ResponseEntity<String> importPgn(@RequestHeader ("Authorization") String token,@RequestBody ApiRequest request)
      throws JsonProcessingException {



    String databaseId = request.getDatabaseId();
    String userId = request.getUserId();
    Optional<TenantEntity> opt = databaseHelperService.getFromToken(token);
    if (opt.isPresent()) {
      TenantEntity tenantEntity = opt.get();

      MessageToParser messageGame = new MessageToParser();
      messageGame.setTenantId(tenantEntity.getId());
      messageGame.setFolderToParse("./data/big");
      messageGame.setDatabaseName("chessvger_admin_database");
      messageGame.setSchema("main");  // TODO renommer
      messageGame.setActionQueue(ActionQueue.PARSEGAME);

      ObjectMapper objectMapper = new ObjectMapper();

      redisMessagePublisher.publish(objectMapper.writeValueAsString(messageGame));

      return ResponseEntity.ok("Requête traitée avec succès pour tenantEntity: " + tenantEntity.getId());
    }
    else {
      log.error("Token importPgn= {}", token);
      return ResponseEntity.badRequest().body("Token importPgn= " + token);
    }
  }

}
