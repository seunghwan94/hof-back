package com.lshwan.hof.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.repository.prod.ProdOptionRepository;

@SpringBootTest
public class ProdOptionRepositoryTests {
  @Autowired
  ProdOptionRepository repository;

  @Test
  public void insertTest(){
    ProdOption option = ProdOption.builder()
    .type("색상")
    .value("빨강")
    .addPrice(2000)
    .build();

    repository.save(option);
  }

  @Test
  public void findAll(){
    repository.findAll();
  }

  
  @Test
  public void findByPnoTest(){
    repository.findByOptionMapsProdPno(406L);
  }
}
