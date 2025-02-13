package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.repository.prod.ProdRepository;

@SpringBootTest
public class ProdRepositoryTests {
  @Autowired
  private ProdRepository repository;
  @Test
  public void insertTest(){
    Prod prod = Prod.builder()
    .cno(ProdCategory.builder().cno(1L).build())
    .title("편안한의자 테스트")
    .content("정말 짱이에용")
    .price(1000)
    .stock(100)
    .build();
    repository.save(prod);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
}
