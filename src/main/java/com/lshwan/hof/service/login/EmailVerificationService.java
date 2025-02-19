package com.lshwan.hof.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lshwan.hof.domain.entity.email.EmailVerification;
import com.lshwan.hof.repository.email.EmailVerificationRepository;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Log4j2
public class EmailVerificationService {

  @Autowired
  private EmailVerificationRepository emailVerificationRepository;
  @Autowired
  private EmailService emailService;

  @Transactional
  public void sendVerificationEmail(String email) {
    
    String verificationCode = UUID.randomUUID().toString(); // 토큰 생성
    LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30); // 만료 시간 설정 (30분)

    // EmailVerification 엔티티 생성
    EmailVerification emailVerification = EmailVerification.builder()
            .email(email)
            .verificationCode(verificationCode)
            .expiresAt(expiresAt)
            .verified(false)  // 초기 인증 상태는 false
            // .memberDetail(memberDetail)
            .build();

    // 이메일 인증 데이터 DB에 저장
    emailVerificationRepository.save(emailVerification);

    // 인증 토큰을 이메일로 발송 (EmailService 사용)
    emailService.sendEmail(email, "이메일 인증 코드", "인증 코드: " + verificationCode);
  }

  @Transactional
  public boolean verifyEmail(String verificationCode) {
    log.info("Attempting to verify token: {}", verificationCode);  // 검증하려는 인증 코드
    // 이메일 인증 코드 조회
    EmailVerification emailVerification = emailVerificationRepository.findByVerificationCode(verificationCode);
    log.info("emailVerification" + emailVerification);
    if (emailVerification == null) {
      log.error("Token not found: {}", verificationCode);  // 토큰을 찾지 못한 경우
      throw new IllegalArgumentException("Invalid token");
    }
    log.info("Found email verification: {}", emailVerification);  // 이메일 인증 정보 확인
    // 이미 인증된 토큰인 경우 처리
    if (emailVerification.isVerified()) {
      throw new IllegalArgumentException("이미 인증되었습니다.");
    }
    
    // 토큰이 만료되었는지 확인
    if (emailVerification.getExpiresAt().isBefore(LocalDateTime.now())) {
      log.error("Token expired: {}", emailVerification);  // 만료된 토큰
      throw new IllegalArgumentException("토큰 만료");
    }
  
    // 이메일 인증 완료 처리
    emailVerification.setVerified(true);
    emailVerificationRepository.save(emailVerification);
    log.info("Email verification successful: {}", emailVerification);  // 인증 성공
    return true; // 인증 성공
  }
}