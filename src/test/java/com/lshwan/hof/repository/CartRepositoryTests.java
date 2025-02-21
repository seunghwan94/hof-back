package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.prod.CartRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CartRepositoryTests {
  @Autowired
  private CartRepository repository;

  @Test
  public void findAll(){
    log.info(repository.findAll()); 
  }
}
