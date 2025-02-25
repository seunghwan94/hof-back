package com.lshwan.hof.repository.email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.email.EmailVerification;
import com.lshwan.hof.domain.entity.member.MemberDetail;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
  EmailVerification findByVerificationCode(String verificationCode);
  // Member findByEmail(String email); 
  Optional<EmailVerification> findByEmail(String email); // Optional을 반환
  // MemberDetail findByEmail(String email); 
  // EmailVerification findByMemberDetail_(String email);

}
