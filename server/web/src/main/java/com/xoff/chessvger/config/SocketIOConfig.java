package com.xoff.chessvger.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketIOConfig {

  @Bean
  public SocketIOServer socketIOServer() {
    com.corundumstudio.socketio.Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092); // Remplacez par le port de votre choix
    return new SocketIOServer(config);
  }
}