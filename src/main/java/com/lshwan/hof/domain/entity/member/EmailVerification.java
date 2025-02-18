package com.lshwan.hof.domain.entity.member;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class EmailVerification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @OneToOne
  private MemberDetail memberDetail;

  private String emailToken;
  private LocalDateTime expirationDate;
  private boolean isVerified;
}
