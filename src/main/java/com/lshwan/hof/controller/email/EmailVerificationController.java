package com.lshwan.hof.controller.email;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.service.login.EmailVerificationService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
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
  @Operation(summary = "이메일 발송 요청 api", description = "받은 파라미터값에 이메일을 발송합니다")
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
  @Operation(summary = "이메일 인증번호 확인", description = "이메일 인증번호가 맞는지 틀린지 확인합니다")
  // public ResponseEntity<String> verifyEmail(@RequestParam String verificationCode) {
  public ResponseEntity<String> verifyEmail(@RequestBody Map<String, String> request) {
    String verificationCode = request.get("verificationCode");
      
    log.info("받은 인증 코드: {}", verificationCode);
    try {
        boolean isVerified = emailVerificationService.verifyEmail(verificationCode);
        if (isVerified) {
            return ResponseEntity.ok("이메일 인증 성공");
        } else {
            return ResponseEntity.badRequest().body("이메일 인증 실패: 인증되지 않은 코드");
        }
    } catch (IllegalArgumentException e) {
        log.error("이메일 인증 실패: {}", e.getMessage());
        return ResponseEntity.badRequest().body("이메일 인증 실패: " + e.getMessage());
    }
  }
}
