package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberHistoryAdmin;

import com.lshwan.hof.domain.entity.member.MemberHistoryAdmin.AdminAction;
import com.lshwan.hof.repository.member.MemberHistoryAdminRepository;

@SpringBootTest
public class MemberHistoryAdminRepositoryTests {
  @Autowired
  private MemberHistoryAdminRepository repository;
  @Test
  public void insertTest(){
    MemberHistoryAdmin entity = MemberHistoryAdmin.builder()
    .adminMno(Member.builder().mno(5L).build())
    .action(AdminAction.PRODUCT_UPDATED)
    .targetNo(167L)
    .description("상품 167번 수정 테스트입니다")
    .ipAddress("255.15.79")

    .build();
    repository.save(entity);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
}
