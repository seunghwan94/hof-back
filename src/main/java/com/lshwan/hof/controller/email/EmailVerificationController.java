package com.lshwan.hof.controller.email;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.service.login.EmailVerificationService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/signup")
@Log4j2
public class EmailVerificationController {

  @Autowired
  private EmailVerificationService emailVerificationService;

    /**
   * 이메일 인증 코드 발송 API
   * @param email 인증할 이메일
   * @return 인증 코드 발송 성공 여부
   */
  @PostMapping("/emailsend")
  // public ResponseEntity<String> sendVerificationEmail(@RequestParam("email") String email) {
    public ResponseEntity<String> sendVerificationEmail(@RequestBody Map<String, String> request) {
      String email = request.get("email");
  
    log.info("이메일 인증 요청 받음: " + email);
    try {
        emailVerificationService.sendVerificationEmail(email);
        return ResponseEntity.ok("이메일 인증 코드가 발송되었습니다.");
    } catch (Exception e) {
        return ResponseEntity.status(400).body("이메일 인증 코드 발송 실패: " + e.getMessage());
    }
  }

  /**
   * 이메일 인증 코드 검증 API
   * @param emailToken 인증 토큰
   * @return 인증 성공/실패 여부
   */
  // @GetMapping("/verify")
  @PostMapping("/verify")
  // public ResponseEntity<String> verifyEmail(@RequestParam String verificationCode) {
  public ResponseEntity<String> verifyEmail(@RequestBody Map<String, String> request) {
    String verificationCode = request.get("verificationCode");
      
    log.info("받은토큰 verification code: {}", verificationCode);
    try {
      // String decodedCode = URLDecoder.decode(verificationCode, StandardCharsets.UTF_8);
      // log.info("Decoded token: {}", decodedCode);
      // emailVerificationService.verifyEmail(decodedCode);
      emailVerificationService.verifyEmail(verificationCode);
      return ResponseEntity.ok("이메일 인증 성공");
    } catch (IllegalArgumentException e) {
      log.error("Email verification failed: {}", e.getMessage());
      return ResponseEntity.status(400).body("이메일 인증 실패: " + e.getMessage());
    }
  }
}
