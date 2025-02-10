package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.common.PushMsg;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.repository.common.PushMsgRepository;

@SpringBootTest
public class PushMsgRepositoryTests {
  @Autowired
  private PushMsgRepository repository;

  @Test
  public void insertTest(){
    PushMsg entity = PushMsg.builder()
    .member(Member.builder().mno(5L).build())
    .category(ProdCategory.builder().cno(1L).build())
    .build();
    repository.save(entity);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
}
