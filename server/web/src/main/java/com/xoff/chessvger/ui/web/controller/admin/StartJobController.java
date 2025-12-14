package com.xoff.chessvger.ui.web.controller.admin;

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

    @GetMapping("/jobCreateEnv")
    String jobCreateEnv() throws JsonProcessingException {
        log.info("jobCreateEnv");
        MessageToParser message = new MessageToParser();
        message.setActionQueue(ActionQueue.CREATE_TENANT_ENVIRONMENT);

        ObjectMapper objectMapper = new ObjectMapper();

        redisMessagePublisher.publish(objectMapper.writeValueAsString(message));
        return "ok";
    }
@GetMapping("/jobInitSystem")
    String jobInitSystem() throws JsonProcessingException {
        log.info("jobInitSystem");
        MessageToParser message = new MessageToParser();
        message.setActionQueue(ActionQueue.INIT_SYSTEM);

        ObjectMapper objectMapper = new ObjectMapper();

        redisMessagePublisher.publish(objectMapper.writeValueAsString(message));
        return "ok";
    }

}
