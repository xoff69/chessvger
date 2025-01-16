package com.xoff.chessvger.ui.service;


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
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class RedisMessageSubscriber implements MessageListener {


  private ApiService apiService;

  public RedisMessageSubscriber(ApiService apiService){
    this.apiService = apiService;
  }


  public void onMessage(final Message message, final byte[] pattern) {

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      MessageFromParser messageFromParser =
          objectMapper.readValue(message.getBody(), MessageFromParser.class);

      System.out.println("RedisMessageSubscriber web Message received: " + new String(message.getBody()));

      String param=MessageFromParser.toJSon(messageFromParser);
      System.out.println(apiService +"RedisMessageSubscriber web Message received: " );
      String url="http://localhost:8080/send?message="+param;
      apiService.callExternalApi(url);

    } catch (IOException e) {
      System.out.println("Error parsing JSON message: " + e.getMessage());
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }


  }
}