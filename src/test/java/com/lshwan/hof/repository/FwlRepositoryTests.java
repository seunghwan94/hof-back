package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.admin.FwlRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class FwlRepositoryTests {
  @Autowired
  private FwlRepository repository;
  
  @Test
  public void findAll(){
    repository.findAll();
  }
}
