package com.xoff.chessvger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // Configure un broker simple en mémoire
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    System.out.println("Registering stomp endpoints");
    // Point de terminaison WebSocket accessible par le client
    registry.addEndpoint("/ws") .setAllowedOrigins("http://localhost:3002").withSockJS();
    //registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
  }
}