package com.xoff.chessvger.ui.web.controller.tomigrate;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @MessageMapping("/send")
  @SendTo("/topic/messages")
  public String handleMessage(String message) {
    System.out.println("WebSocketController:+"+message);
    return message; // Renvoie le message à tous les abonnés
  }
}