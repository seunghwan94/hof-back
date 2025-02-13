package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.note.NoteRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class NoticeRepositoryTests {
  @Autowired
  private NoteRepository repository;

  @Test
  public void findAll(){
    log.info(repository.findAll()); 
  }
  
}
