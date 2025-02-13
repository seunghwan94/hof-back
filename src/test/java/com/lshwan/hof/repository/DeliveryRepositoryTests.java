package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.order.DeliveryRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class DeliveryRepositoryTests {
  @Autowired
  private DeliveryRepository repository;

  @Test
  public void findAll(){
    repository.findAll();
  }
}
