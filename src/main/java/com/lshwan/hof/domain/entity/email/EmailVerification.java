package com.lshwan.hof.domain.entity.email;

import java.time.LocalDateTime;

import com.lshwan.hof.domain.entity.member.MemberDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_email_verification")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno", referencedColumnName = "mno")
  private MemberDetail memberDetail;

  @Column(nullable = false)
  private String email;  // 인증할 이메일

  @Column(nullable = false)
  private String verificationCode;  // 인증 코드

  @Column(nullable = false)
  private boolean verified = false;  // 인증 여부 (기본값은 false)

  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();  // 생성 시간 (기본값은 현재 시간)

  @Column(nullable = false)
  private LocalDateTime expiresAt;  // 만료 시간


  // 인증 완료 메서드
  public void verify() {
      this.verified = true;
  }

  // 이메일 인증 생성자
  public static EmailVerification createEmailVerification(MemberDetail memberDetail, String email, String verificationCode, LocalDateTime expiresAt) {
      return EmailVerification.builder()
              .memberDetail(memberDetail)
              .email(email)
              .verificationCode(verificationCode)
              .expiresAt(expiresAt)
              .build();
  }

  public boolean isVerified() {
    return this.verified;
}
}
