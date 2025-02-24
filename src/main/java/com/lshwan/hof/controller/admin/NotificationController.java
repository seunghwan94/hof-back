package com.lshwan.hof.controller.admin;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("admin/notify")
public class NotificationController {
  @PostMapping("/send")
    // public ResponseEntity<String> sendNotification(@RequestBody Map<String, String> payload) {
    // String message = payload.get("message");
    //     return ResponseEntity.ok("알림 전송: " + message);
    // }
  public ResponseEntity<String> sendNotification(@RequestParam("message") String message) {
      return ResponseEntity.ok("알림 전송: " + message);
  }
}
