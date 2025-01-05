package com.xoff.chessvger.ui.web.controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
public class MessageToClientHandler extends TextWebSocketHandler {

  // Liste des sessions connectées
  private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    sessions.add(session);
    System.out.println("Nouvelle connexion : " + session.getId());
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
    System.out.println("Message reçu : " + message.getPayload());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
    sessions.remove(session);
    System.out.println("Connexion fermée : " + session.getId());
  }

  // Méthode pour envoyer des messages à tous les clients
  public void broadcast(String message) {
    for (WebSocketSession session : sessions) {
      try {
        session.sendMessage(new TextMessage(message));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}