package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.order.OrderRepository;

import lombok.extern.log4j.Log4j2;
@SpringBootTest
@Log4j2
public class OrderRepositoryTests {
  @Autowired
  private OrderRepository repository;

  @Test
  public void findAll(){
    repository.findAll();
  }

  @Test
  public void findByIdTests(){
    repository.findById(1L);
  }
}
