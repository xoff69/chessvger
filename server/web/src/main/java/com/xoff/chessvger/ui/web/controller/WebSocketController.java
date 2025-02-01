package com.xoff.chessvger.ui.web.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

// Créez un contrôleur WebSocket qui traite les messages STOMP.
@Controller
public class WebSocketController {

  //@CrossOrigin(origins = "http://localhost:3000")
  @MessageMapping("/app/hello") // Reçoit les messages du client
  @SendTo("/topic/greetings") // Diffuse les messages à tous les abonnés
  public String handleGreeting(String message) {

    System.out.println("handleGreeting message: " + message);

    return "Bonjour, " + message + "!";
  }
}