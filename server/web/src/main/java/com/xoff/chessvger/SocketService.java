package com.xoff.chessvger;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

  private final SocketIOServer server;

  public SocketService(SocketIOServer server) {
    this.server = server;
    this.server.addConnectListener(client -> {
      System.out.println("Client connected: " + client.getSessionId());
    });

    this.server.addDisconnectListener(client -> {
      System.out.println("Client disconnected: " + client.getSessionId());
    });

    this.server.start();
  }

  public void sendMessage(String event, Object data) {
    server.getBroadcastOperations().sendEvent(event, data);
  }
}