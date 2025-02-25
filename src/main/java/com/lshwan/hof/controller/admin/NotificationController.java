package com.lshwan.hof.controller.admin;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.handler.NotificationWebSocketHandler;


@RestController
@RequestMapping("admin/notify")

public class NotificationController {
  private final NotificationWebSocketHandler webSocketHandler;

    public NotificationController(NotificationWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
  @PostMapping("/send")

  public ResponseEntity<String> sendNotification(@RequestParam("message") String message) {
    try {
      webSocketHandler.sendMessageToAll(message);
      return ResponseEntity.ok("알림 전송: " + message);
  } catch (IOException e) {
      return ResponseEntity.status(500).body("알림 전송 실패");
  }
  }
}
