package com.lshwan.hof.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

  private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
      sessions.add(session);
      System.out.println("✅ 새로운 WebSocket 연결: " + session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
    for (WebSocketSession s : sessions) {
        if (s.isOpen()) {
            s.sendMessage(message);
        }
    }  
    System.out.println("📩 받은 메시지: " + message.getPayload());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
      sessions.remove(session);
      System.out.println("🚫 WebSocket 연결 종료: " + session.getId());
  }

  public void sendMessageToAll(String message) throws IOException {
    for (WebSocketSession session : sessions) {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
    System.out.println("메세지 전송완료 " + message);
}
}