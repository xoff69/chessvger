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
  @Deprecated
  @GetMapping("/jobInitSystem")
  String jobInitSystem() throws JsonProcessingException {
    log.info("jobInitSystem");
    MessageToParser message=new MessageToParser();
    message.setActionQueue(ActionQueue.INIT_SYSTEM);

    ObjectMapper objectMapper=new ObjectMapper();

    redisMessagePublisher.publish(objectMapper.writeValueAsString(message));
    return "ok";
  }
  @GetMapping("/jobCreateEnv")
  String jobCreateEnv() throws JsonProcessingException {
    log.info("jobDatabase");
    MessageToParser message=new MessageToParser();
    message.setActionQueue(ActionQueue.CREATE_TENANT_ENVIRONMENT);

    ObjectMapper objectMapper=new ObjectMapper();

    redisMessagePublisher.publish(objectMapper.writeValueAsString(message));
    return "ok";
  }

  @GetMapping("/jobGame")
  // TODO signature, try catch
  String jobGame() throws JsonProcessingException {
    log.info("jobGame");

    MessageToParser messageGame=new MessageToParser();
    messageGame.setFolderToParse("./data/twic1997");
    messageGame.setDatabaseName("chessvger_admin_database");
    messageGame.setSchema("main_admin");  // TODO renommer
    messageGame.setActionQueue(ActionQueue.PARSEGAME);

    ObjectMapper objectMapper=new ObjectMapper();

    redisMessagePublisher.publish(objectMapper.writeValueAsString(messageGame));
    return "ok";
  }

}
