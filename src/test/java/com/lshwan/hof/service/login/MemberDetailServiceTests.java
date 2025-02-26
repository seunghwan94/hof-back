package com.lshwan.hof.service.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberDetailServiceTests {
  @Autowired
  private MemberDetailService service;

  @Test
  void getMemberDetailTest(){
    log.info("detailTest start");
    log.info("Dto : " + service.getMemberDetail(204L));
    log.info("detailTest end");
  }
}
