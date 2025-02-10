package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberHistoryAdmin;
import com.lshwan.hof.repository.member.MemberHistoryRepository;

@SpringBootTest
public class MemberHistoryAdminRepositoryTests {
  @Autowired
  private MemberHistoryRepository repository;
  @Test
  public void insertTest(){
    // MemberHistoryAdmin entity = MemberHistoryAdmin.builder()
    // .adminMno(Member.builder().mno(5L).build())
    // .build();
  }
}
