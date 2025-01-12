package com.xoff.chessvger.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.topic.MessageFromParser;
import com.xoff.chessvger.topic.MessageToParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public static List<String> messageList = new ArrayList<>();

  public void onMessage(final Message message, final byte[] pattern) {

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      MessageFromParser messageFromParser =
          objectMapper.readValue(message.getBody(), MessageFromParser.class);

      messageList.add(message.toString());
      System.out.println("RedisMessageSubscriber web Message received: " + new String(message.getBody()));
      messagingTemplate.convertAndSend("/topic/notifications", messageFromParser.getMessage());
    } catch (IOException e) {
      System.out.println("Error parsing JSON message: " + e.getMessage());
      throw new RuntimeException(e);
    }


  }
}