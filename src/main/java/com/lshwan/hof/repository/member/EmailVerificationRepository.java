package com.lshwan.hof.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.EmailVerification;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
  EmailVerification findByEmailToken(String emailToken); 
}
