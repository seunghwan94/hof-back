package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.history.SearchHistoryRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SearchHistoryRepositoryTests {
  @Autowired
  private SearchHistoryRepository repository;

  @Test
  public void findAll() {
    log.info(repository.findAll());
  }
}
