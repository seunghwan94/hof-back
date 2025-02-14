package com.lshwan.hof.service.prod;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdCategory;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ProdServiceTests {
  @Autowired
  private ProdService service;
  @Autowired
  private ProdCategoryService categoryService;

  @Test
  public void addTest(){
    // target & get
    Long cno = 1L;
    ProdCategory prodCategory = categoryService.findBy(cno);
    Prod prod = Prod.builder()
        .cno(prodCategory)
        .title("title test")
        .content("content test")
        .price(10000)
        .stock(10)
      .build();
    // when
    Prod prod2 = service.add(prod);
    // then
    assertNotNull(prod2);
  }

  @Test
  public void findBy(){
    // target
    Long pno = 1L;
    // get & when
    Prod prod = service.findBy(pno);
    // then
    assertNotNull(prod.getCno());
  }

  @Test
  public void findList(){
  // when
    List<Prod> list = service.findList();
    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void modify(){
    // given
    Long cno = 1L;
    ProdCategory prodCategory = categoryService.findBy(cno);
    Prod prod = Prod.builder()
        .cno(prodCategory)
        .title("title test")
        .content("content test")
        .price(10000)
        .stock(10)
      .build();
    // when
    Prod prod2 = service.add(prod);
    // then
    assertNotNull(prod2);

    // when
    Prod modiProd = Prod.builder()
        .cno(prodCategory)
        .title("title test")
        .price(15000)
      .build();

    Long modiCno = service.modify(modiProd);

    // then
    assertNotNull(modiCno);
    Prod targetProd = service.findBy(modiCno);
    assertNotNull(targetProd);
    assertTrue(targetProd.getPrice() == 15000);
  }

  @Test
  public void remove(){
    // given
    Long cno = 1L;
    ProdCategory prodCategory = categoryService.findBy(cno);

    Prod prod = Prod.builder()
        .cno(prodCategory)
        .title("title test")
        .content("content test")
        .price(10000)
        .stock(10)
      .build();
    Prod prod2 = service.add(prod);
    assertNotNull(prod2);

    // when
    boolean isRemove = service.remove(prod2.getPno());

    // then
    assertTrue(isRemove);
  }
}
