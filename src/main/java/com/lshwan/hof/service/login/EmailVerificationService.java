package com.lshwan.hof.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lshwan.hof.domain.entity.email.EmailVerification;
import com.lshwan.hof.domain.entity.member.MemberDetail;
import com.lshwan.hof.repository.email.EmailVerificationRepository;
import lombok.extern.log4j.Log4j2;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@Log4j2
public class EmailVerificationService {

  @Autowired
  private EmailVerificationRepository emailVerificationRepository;
  @Autowired
  private MemberService memberService;
  @Autowired
  private EmailService emailService;

  @Transactional
  public void sendVerificationEmail(String email) {
    // // 이메일이 이미 인증된 상태인지 확인
    // Optional<EmailVerification> existingVerification = emailVerificationRepository.findByEmail(email);

    // if (existingVerification.isPresent()) {
    //   // 인증이 이미 완료된 이메일이면 예외 처리
    //   if (existingVerification.get().isVerified()) {
    //     log.info("이메일이 이미 인증되었습니다: " + email);
    //     throw new IllegalArgumentException("이 이메일은 이미 인증되었습니다.");
    //   }
    //   // 인증이 완료되지 않은 이메일이면 기존 인증 데이터를 갱신할 수 있음
    //   log.info("인증되지 않은 이메일에 대해 새 인증 코드 발송: " + email);
    // } else {
    //   // 새로운 이메일인 경우 이메일 인증 데이터를 새로 생성
    //   log.info("새 이메일에 대해 인증 코드 발송: " + email);
    // }
    
    //
    try {
      // 이메일 발송 로직 (예시)
      log.info("이메일 발송 시작: " + email);
      // 이메일 발송 코드
      // 예: emailSender.send(email, code);
    } catch (Exception e) {
      log.error("이메일 발송 실패: " + email, e);
      throw new RuntimeException("이메일 발송 실패", e);
    }

    //
    // String verificationCode = UUID.randomUUID().toString(); // 토큰 생성
    String verificationCode = generateVerificationCode();
    LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(500); // 만료 시간 설정 (30분),500qns

    MemberDetail memberDetail = memberService.verificationBefore(email);

    if(!memberDetail.getEmail().equals(email)){
      throw new RuntimeException("이상한 경로");
    }

    EmailVerification emailVerification = EmailVerification.builder()
      .memberDetail(memberDetail)
      .email(email)
      .verificationCode(verificationCode)
      .expiresAt(expiresAt)
    .build();

    // 이메일 인증 데이터 DB에 저장
    emailVerificationRepository.save(emailVerification);

    // 인증 토큰을 이메일로 발송 (EmailService 사용)
    emailService.sendEmail(email, "이메일 인증 코드", "인증 코드: " + verificationCode);
  }


  private String generateVerificationCode() {
    SecureRandom random = new SecureRandom();
    StringBuilder code = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      code.append(random.nextInt(10));  // 0부터 9까지의 숫자 중 랜덤하게 선택
    }
    return code.toString();
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