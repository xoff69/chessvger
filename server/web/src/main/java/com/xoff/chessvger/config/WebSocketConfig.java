package com.xoff.chessvger.config;

import com.xoff.chessvger.ui.web.controller.tomigrate.MessageToClientHandler;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new MessageToClientHandler(), "/ws/messageToClient").setAllowedOrigins("*");
  }
}
