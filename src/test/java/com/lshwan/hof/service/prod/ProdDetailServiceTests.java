package com.lshwan.hof.service.prod;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.prod.ProdDetailDto;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ProdDetailServiceTests {

  @Autowired
  private ProdDetailService service;

  @Test
  public void findByTest() {
    // given
    Long pno = 406L;
    // when
    ProdDetailDto foundProduct = service.findBy(pno);
    // then
    assertNotNull(foundProduct);
  }

}
