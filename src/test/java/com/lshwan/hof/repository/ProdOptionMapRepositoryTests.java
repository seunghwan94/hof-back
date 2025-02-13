package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.repository.prod.ProdOptionMapRepository;

@SpringBootTest
public class ProdOptionMapRepositoryTests {
  @Autowired
  private ProdOptionMapRepository repository;
  
  @Test
  public void insertTest(){
    ProdOptionMap map = ProdOptionMap.builder()
    .prod(Prod.builder().pno(1L).build())
    .option(ProdOption.builder().no(1L).build())
    .stock(70)
    .build();
    repository.save(map);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
    
}
