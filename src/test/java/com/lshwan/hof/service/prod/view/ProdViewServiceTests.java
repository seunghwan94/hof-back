package com.lshwan.hof.service.prod.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.prod.view.ProdView;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProdViewServiceTests {
  @Autowired
  private ProdViewService service;

  @Test
  public void findByTest(){
    // target
    Long pno = 19L;
    // get & when
    ProdView prodView = service.findBy(pno);
    // then
    assertNotNull(prodView.getPno());
  }

  @Test
  public void findByTitleTest(){
    // target
    String title = "의자";
    // get & when
    List<ProdView> list = service.findByTitle(title);
    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void findByCnoTest(){
    // target
    Long cno = 1L;
    // get & when
    List<ProdView> list = service.findByCno(cno);
    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void findListTest(){
    // when
    List<ProdView> list = service.findList();

    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

}
