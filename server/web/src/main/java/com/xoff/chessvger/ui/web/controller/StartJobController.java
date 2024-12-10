package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.config.RedisMessagePublisherGame;
import com.xoff.chessvger.config.RedisMessagePublisherPlayer;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StartJobController {
@Autowired
RedisMessagePublisherPlayer redisMessagePublisher;

  @Autowired
  RedisMessagePublisherPlayer redisMessagePublisherPlayer;

  @Autowired
  RedisMessagePublisherGame redisMessagePublisherGame;

  @GetMapping("/jobPlayer")
 String jobPlayer() {
    log.info("jobPlayer");
    String message = "Message " + UUID.randomUUID();
    redisMessagePublisherPlayer.publish(message);
    return "ok";
  }

  @GetMapping("/jobGame")
  String jobGame() {
    log.info("jobGame");
    String message = "Message " + UUID.randomUUID();
    redisMessagePublisherGame.publish(message);
    return "ok";
  }

}
