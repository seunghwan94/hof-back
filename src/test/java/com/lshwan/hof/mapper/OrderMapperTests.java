package com.lshwan.hof.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class OrderMapperTests {
  @Autowired
  private OrderMapper mapper;

  @Test
  void test(){
    log.info(mapper.findOrderHistoryByMember(204L));
  }
}
