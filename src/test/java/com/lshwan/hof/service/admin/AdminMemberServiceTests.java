package com.lshwan.hof.service.admin;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.member.AdminMemberDto;
import com.lshwan.hof.service.login.MemberService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class AdminMemberServiceTests {
  @Autowired
  private MemberService adminMemberService;

  @Test
  public void adminMemberListTest() {
    // when
    List<AdminMemberDto> list = adminMemberService.adminMemberList();

    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
    
    // 로그 출력 (확인용)
    list.forEach(member -> log.info("Member: {}", member));
  }
}
