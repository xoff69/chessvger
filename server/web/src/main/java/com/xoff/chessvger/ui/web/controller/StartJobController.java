package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.config.RedisMessagePublisher;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StartJobController {
@Autowired
  RedisMessagePublisher redisMessagePublisher;

  @GetMapping("/jobPlayer")
 String jobPlayer() {
    log.info("jobPlayer");
    String message = "Message " + UUID.randomUUID();
    redisMessagePublisher.publish(message);
    return "ok";
  }

}
