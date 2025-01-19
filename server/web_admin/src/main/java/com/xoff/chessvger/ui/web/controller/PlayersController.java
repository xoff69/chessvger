package com.xoff.chessvger.ui.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.repository.CommonPlayerEntity;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
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
  RedisMessagePublisher redisMessagePublisher;

  @Autowired
  IPlayerService iPlayerService;

  @GetMapping("/apiadmin/players/all")
  public ResponseEntity<List<CommonPlayerEntity>> all(){
    return new ResponseEntity<>(iPlayerService.findAll(),
        HttpStatus.OK);
  }
  @GetMapping("/apiadmin/players/count")
  public ResponseEntity<Long> count(){
    return new ResponseEntity<>(iPlayerService.count(),
        HttpStatus.OK);
  }
  @GetMapping("/apiadmin/players/import")
    // TODO signature, try catch
  public ResponseEntity<String> jobPlayer() throws JsonProcessingException {
    log.info("jobPlayer");
    MessageToParser message=new MessageToParser();
    message.setFolderToParse("./data/players_list_xml_foa.xml");
    message.setSchema("common");
    message.setActionQueue(ActionQueue.PARSEPLAYER);

    ObjectMapper objectMapper=new ObjectMapper();

    redisMessagePublisher.publish(objectMapper.writeValueAsString(message));
    return new ResponseEntity<>("run",
        HttpStatus.OK);
  }
}
