package com.lshwan.hof.controller.admin;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.handler.NotificationWebSocketHandler;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("admin/notify")

public class NotificationController {
  private final NotificationWebSocketHandler webSocketHandler;

    public NotificationController(NotificationWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
  @PostMapping("/send")
  @Operation(summary = "Notification 알람메시지 전송", description = "자바 웹소켓 연결되어있는 안드로이드 혹은 리액트에 알람메시지를 보냅니다")
  public ResponseEntity<String> sendNotification(@RequestParam("message") String message) {
    try {
      webSocketHandler.sendMessageToAll(message);
      return ResponseEntity.ok("알림 전송: " + message);
  } catch (IOException e) {
      return ResponseEntity.status(500).body("알림 전송 실패");
  }
  }
}
