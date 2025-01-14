package com.xoff.chessvger.ui.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

  private final RestTemplate restTemplate;

  public ApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String callExternalApi(String url) {
    // TODO essayer de mettre un objet MessageFromParser  @see RedisMessageSubscriber
    System.out.println("Calling external api: " + url);
    return restTemplate.getForObject(url, String.class);
  }
}
