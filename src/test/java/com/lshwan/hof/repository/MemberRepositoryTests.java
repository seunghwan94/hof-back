package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.member.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
  @Autowired
  private MemberRepository repository;

  @Test
  @Transactional
  public void findAll(){
    log.info(repository.findAll()); 
  }

  @Test
  public void findByTests(){
    Member member = repository.save(Member.builder().build());
    log.info("test : " + member.getMno()); 
  }
  
}
