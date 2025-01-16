package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.topic.MessageFromParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

  private final RestTemplate restTemplate;

  public ApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public MessageFromParser callExternalApi(String url) {
    // TODO essayer de mettre un objet MessageFromParser  @see RedisMessageSubscriber
    System.out.println("Calling external api: " + url);
    return restTemplate.getForObject(url, MessageFromParser.class);
  }
}
