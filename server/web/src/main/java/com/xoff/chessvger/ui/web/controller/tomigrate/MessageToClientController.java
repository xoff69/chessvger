package com.xoff.chessvger.ui.web.controller.tomigrate;
import org.springframework.web.bind.annotation.*;

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
