package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.email.EmailVerificationRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class EmailVerificationRepositoryTests {
  @Autowired
  private EmailVerificationRepository emailVerificationRepository;

  @Test
  public void findByVerificationCode() {
    log.info(emailVerificationRepository.findByVerificationCode("eda84893-30ce-4949-a854-fac64438366f"));
  }
}
