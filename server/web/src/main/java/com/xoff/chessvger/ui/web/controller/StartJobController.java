package com.xoff.chessvger.ui.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.config.RedisMessageReceiver;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StartJobController {
@Autowired
RedisMessageReceiver redisMessageReceiver;



  @Autowired
  RedisMessagePublisher redisMessagePublisher;

  @GetMapping("/jobPlayer")
    // TODO signature, try catch
 String jobPlayer() throws JsonProcessingException {
    log.info("jobPlayer");
    MessageToParser message=new MessageToParser();
    message.setFolderToParse("./data/players_list_xml_foa.xml");
    message.setSchema("common");
    message.setActionQueue(ActionQueue.PARSEPLAYER);

    ObjectMapper objectMapper=new ObjectMapper();

   redisMessagePublisher.publish(objectMapper.writeValueAsString(message));
    return "ok";
  }
  @GetMapping("/jobDatabase")
  String jobDatabase() {
    log.info("jobDatabase");
    // TODO envoi message cree la db
    //String message = "Message " + UUID.randomUUID();
    //redisMessagePublisherPlayer.publish(message);
    return "ok";
  }

  @GetMapping("/jobGame")
  // TODO signature, try catch
  String jobGame() throws JsonProcessingException {
    log.info("jobGame");

    MessageToParser messageGame=new MessageToParser();
    messageGame.setFolderToParse("./data/twic");
    messageGame.setSchema("common");
    messageGame.setActionQueue(ActionQueue.PARSEGAME);

    ObjectMapper objectMapper=new ObjectMapper();

    redisMessagePublisher.publish(objectMapper.writeValueAsString(messageGame));
    return "ok";
  }

}
