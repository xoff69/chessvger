package com.xoff.chessvger.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ApiService {

  private final RestTemplate restTemplate;

  public ApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String callExternalApi(String url) throws IOException, InterruptedException {
    // TODO essayer de mettre un objet MessageFromParser  @see RedisMessageSubscriber
    log.info("Calling external api: " + url);
    HttpClient client = HttpClient.newHttpClient();

    // Construire une requête GET
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

    // Envoyer la requête et récupérer la réponse
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Afficher la réponse
    log.info("Status Code: " + response.statusCode());
    log.info("Response Body: " + response.body());
    return response.body();
  }

  public String callExternalApi(String url,String ids[]) throws IOException, InterruptedException {
    // TODO essayer de mettre un objet MessageFromParser  @see RedisMessageSubscriber
    log.info("Calling external api bis: " + url);
    HttpClient client = HttpClient.newHttpClient();

    String queryString = String.join(",", ids);
    String urlSend =url+ "?ids=" + queryString;
    log.info("urlSend"+urlSend);
    // Construire une requête GET
    // TODO ajouter le tokem
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(urlSend))
            .GET()
            .build();

    // Envoyer la requête et récupérer la réponse
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Afficher la réponse
    log.info("Status Code: " + response.statusCode());
    log.info("Response Body: " + response.body());
    return response.body();
  }
}
