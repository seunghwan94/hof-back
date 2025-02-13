package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.history.MemberHistoryLogin;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.history.MemberHistoryLoginRepository;

@SpringBootTest
public class MemberHistoryLoginRepositoryTests {
  @Autowired
  private MemberHistoryLoginRepository repository;
  @Test
  public void insertTest(){
    MemberHistoryLogin login = MemberHistoryLogin.builder()
    .member(Member.builder().mno(5L).build())
    .success(true)
    .ipAddress("503-17-97489-4")
    .userAgent("Chrome")
    .failReason(null)
    .build();
    repository.save(login);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
}
