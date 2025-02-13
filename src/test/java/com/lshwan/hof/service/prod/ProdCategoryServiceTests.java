package com.lshwan.hof.service.prod;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.domain.entity.prod.ProdCategory.CategoryType;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ProdCategoryServiceTests {
  @Autowired
  private ProdCategoryService service;

  @Test
  public void addTest(){
    // target
    CategoryType type = CategoryType.기타;
    // get
    ProdCategory prodCategory = ProdCategory.builder()
        .type(type)
      .build();
    // when
    Long cno = service.add(prodCategory);
    // then
    assertTrue(cno > 0);
  }

  @Test
  public void findBy(){
    // target
    Long cno = 1L;
    // get & when
    ProdCategory prodCategory = service.findBy(cno);
    log.info("prodCategoryCno : {}", prodCategory.getCno());
    // then
    assertNotNull(prodCategory.getCno());
  }

  @Test
  public void findList(){
    // when
    List<ProdCategory> list = service.findList();

    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void modify(){
    // given
    ProdCategory prodCategory = ProdCategory.builder()
        .type(CategoryType.기타)
      .build();
    Long cno = service.add(prodCategory);
    assertNotNull(cno);

    // when
    ProdCategory modiCategory = ProdCategory.builder()
        .cno(cno)
        .type(CategoryType.침대)
      .build();
    Long modiCno = service.modify(modiCategory);

    // then
    assertNotNull(modiCno);
    ProdCategory targetCategory = service.findBy(modiCno);
    assertNotNull(targetCategory);
    assertTrue(targetCategory.getType() == CategoryType.침대);
  }

  @Test
  public void remove(){
    // given
    ProdCategory prodCategory = ProdCategory.builder()
        .type(CategoryType.침대)
      .build();
    Long cno = service.add(prodCategory);
    assertNotNull(cno);

    // when
    boolean isRemove = service.remove(cno);

    // then
    assertTrue(isRemove);
  }
}
