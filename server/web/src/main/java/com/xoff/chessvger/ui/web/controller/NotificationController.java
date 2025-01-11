package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.SocketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

  private final SocketService socketService;

  public NotificationController(SocketService socketService) {
    this.socketService = socketService;
  }

  @GetMapping("/notify")
  public String sendNotification() {
    socketService.sendMessage("notification", "Hello, this is a test message!");
    return "Notification sent!";
  }
}