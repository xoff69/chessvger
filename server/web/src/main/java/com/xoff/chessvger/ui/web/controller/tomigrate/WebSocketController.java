package com.xoff.chessvger.ui.web.controller.tomigrate;

import com.xoff.chessvger.socket.MessageSocket;
import com.xoff.chessvger.socket.OutputMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

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