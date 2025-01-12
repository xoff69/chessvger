package com.xoff.chessvger.ui.web.controller.tomigrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class NotificationController {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;
//http://localhost:8080/send?message=totoototot
  @GetMapping("/send")
  public String sendNotification(@RequestParam String message) {
    // Envoie un message à la destination "/topic/notifications"
    messagingTemplate.convertAndSend("/topic/notifications", message);
    System.out.println("sendNotification message: " + message);
    return "Message envoyé : " + message;
  }
}