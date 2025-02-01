package com.xoff.chessvger.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

  private final RestTemplate restTemplate;

  public ApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void callExternalApi(String url) throws IOException, InterruptedException {
    // TODO essayer de mettre un objet MessageFromParser  @see RedisMessageSubscriber
    System.out.println("Calling external api: " + url);
    HttpClient client = HttpClient.newHttpClient();

    // Construire une requête GET
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

    // Envoyer la requête et récupérer la réponse
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Afficher la réponse
    System.out.println("Status Code: " + response.statusCode());
    System.out.println("Response Body: " + response.body());
  }
}
