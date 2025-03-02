package com.xoff.chessvger.ui.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.model.CommonGameModel;
import com.xoff.chessvger.model.DatabaseModel;
import com.xoff.chessvger.repository.CommonGameEntity;
import com.xoff.chessvger.repository.TenantEntity;
import com.xoff.chessvger.service.*;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.ui.JwtUtil;
import com.xoff.chessvger.ui.ResponseList;
import com.xoff.chessvger.ui.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
public class GamesController {
  @Autowired
  IDatabaseService iDatabaseService;

  @Autowired
  GameService gameService;

  @Autowired
  RedisMessagePublisher redisMessagePublisher;

  @Autowired
  DatabaseHelperService databaseHelperService;
  @GetMapping("/api/games/importGames")
    // TODO signature, try catch
  String importGames(@RequestHeader ("Authorization") String token,
                 @RequestParam long databaseId) throws JsonProcessingException {
    log.info("importGames");

    databaseHelperService.setDatasource(token, "common");
    DatabaseModel databaseModel=iDatabaseService.findById(databaseId);
    log.info("databaseid:"+databaseId + "databaseModel:"+databaseModel);


// TODO verifier que le user a le droit de lire cette bd
    MessageToParser messageGame=new MessageToParser();
    messageGame.setFolderToParse("./data/twic1997");
    messageGame.setDatabaseName("chessvger_demo_database");
    messageGame.setSchema(databaseModel.getName());
    messageGame.setActionQueue(ActionQueue.PARSEGAME);

    ObjectMapper objectMapper=new ObjectMapper();

    redisMessagePublisher.publish(objectMapper.writeValueAsString(messageGame));
    return "ok";
  }
  @GetMapping("/api/games/all")
  public ResponseEntity<ResponseList<CommonGameEntity>> all(@RequestHeader ("Authorization") String token,
                                                            @RequestParam long databaseId,@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    // TODO verifier que le user a le droit de lire cette bd
// FIXME on fait deja le count dans le gameservice
    // pas la peine de repondre un response list ?
    databaseHelperService.setDatasource(token, "common");
    DatabaseModel databaseModel=iDatabaseService.findById(databaseId);
  log.info("databaseid:"+databaseId + "databaseModel:"+databaseModel);
    databaseHelperService.setDatasource(token,databaseModel.getName());
    return new ResponseEntity<>(new ResponseList(gameService.findAll(pageable).stream().toList(),gameService.count()),
        HttpStatus.OK);
  }
  @GetMapping("/api/games/findById")
  public ResponseEntity<CommonGameModel>
  findById(@RequestHeader ("Authorization") String token,@RequestParam("id") Long id){


    // TODO trouver le nom a partir du bd id
    databaseHelperService.setDatasource(token,"main");
Optional<CommonGameModel> opt=gameService.findById(id);
if(opt.isPresent()){
    return new ResponseEntity<>(opt.get(),HttpStatus.OK);

  }
else{
    return ResponseEntity.notFound().build();
}}

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
