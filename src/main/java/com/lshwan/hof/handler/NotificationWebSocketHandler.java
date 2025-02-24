package com.lshwan.hof.handler;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotificationWebSocketHandler extends TextWebSocketHandler {

  private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
      sessions.add(session);
      System.out.println("✅ 새로운 WebSocket 연결: " + session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
      System.out.println("📩 받은 메시지: " + message.getPayload());
      broadcast(message.getPayload());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
      sessions.remove(session);
      System.out.println("🚫 WebSocket 연결 종료: " + session.getId());
  }

  public static void broadcast(String message) {
      for (WebSocketSession session : sessions) {
          try {
              session.sendMessage(new TextMessage(message));
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      System.out.println("📢 알림 전송 완료: " + message);
  }
}