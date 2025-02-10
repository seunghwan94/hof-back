package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.common.PushMsg;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberHistory;
import com.lshwan.hof.domain.entity.prod.ProdCategory;

@SpringBootTest
public class MemberHistoryRepositoryTests {
  @Autowired
  private MemberHistoryRepository repository;
  @Test
  public void insertTest(){
    MemberHistory entity = MemberHistory.builder()
    .member(Member.builder().mno(5L).build())
    .action(MemberHistory.MemberAction.LOGIN)
    .description("테스트입니다")
    .build();
    repository.save(entity);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
}
