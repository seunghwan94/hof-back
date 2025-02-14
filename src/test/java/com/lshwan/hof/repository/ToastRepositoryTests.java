package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.common.ToastEntity;

import com.lshwan.hof.repository.common.ToastRepository;


import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ToastRepositoryTests {
  @Autowired
  private ToastRepository repository;

  @Test
  public void insertTest(){
    ToastEntity entity = ToastEntity.builder()
    .member(Member.builder().mno(5L).build())
    .type(ToastEntity.ToastType.INFO)
    .message("테스트1")
    .build();
    repository.save(entity);
  }
  @Test
  public void findall(){
    repository.findAll();
  }
  
}
