package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.prod.ProdCategoryRepository;

@SpringBootTest
public class ProdCategoryRepositoryTests {
  @Autowired
  private ProdCategoryRepository repository;
  @Test
  public void insertTest(){

  }
  @Test
  public void findAll(){
    repository.findAll();
  }
}
