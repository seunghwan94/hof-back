package com.lshwan.hof.repository.email;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.email.EmailVerification;
import com.lshwan.hof.domain.entity.member.Member;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
  EmailVerification findByVerificationCode(String verificationCode);
  Member findByEmail(String email); 
  // MemberDetail findByEmail(String email); 

}
