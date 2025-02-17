package com.lshwan.hof.service.admin;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.admin.FWL;
import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.domain.entity.prod.ProdCategory.CategoryType;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class FwlServiceTests {
  @Autowired
  private FwlService service;
  @Test
  public void addTest(){
    // get
    FWL fwl = FWL.builder()
      .content("개새")
      .build();
    // when
    Long fno = service.add(fwl);
    // then
    assertTrue(fno > 0);
  }

  @Test
  public void findBy(){
    // target
    Long fno = 1L;
    // get & when
    FWL fwl = service.findBy(fno);
    log.info("prodCategoryCno : {}", fwl.getFno());
    // then
    assertNotNull(fwl.getFno());
  }

  @Test
  public void findList(){
    // when
    List<FWL> list = service.findList();

    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void modify(){
    // given
    FWL fwl = FWL.builder()
    .content("개새zx")
      .build();
    Long fno = service.add(fwl);
    assertNotNull(fno);

    // when
    FWL modiCategory = FWL.builder()
    .fno(fno)
    .content("개새zzz")
      .build();
    Long modiCno = service.modify(modiCategory);

    // then
    assertNotNull(modiCno);
    FWL targetCategory = service.findBy(modiCno);
    assertNotNull(targetCategory);
    assertTrue(targetCategory.getFno() == modiCno);
  }

  @Test
  public void remove(){
    // given
    FWL fwl = FWL.builder()
    .content("개새zzzx")
      .build();
    Long fno = service.add(fwl);
    assertNotNull(fno);

    // when
    boolean isRemove = service.remove(fno);

    // then
    assertTrue(isRemove);
  }
}
