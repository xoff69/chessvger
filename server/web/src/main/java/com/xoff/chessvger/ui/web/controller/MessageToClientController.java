package com.xoff.chessvger.ui.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messageToClient")
public class MessageToClientController {

 // private final MessageToClientHandler messageToClientHandler;
/*
  @Autowired
  public MessageToClientController(MessageToClientHandler messageToClientHandler) {
    this.messageToClientHandler = messageToClientHandler;
  }

  @PostMapping("/notify")
  public void notify(@RequestBody String message) {
    messageToClientHandler.broadcast("Mise à jour : " + message);
  }*/
}
