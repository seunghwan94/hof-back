package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.repository.note.NoteRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class NoteRepositoryTests {
  @Autowired
  private NoteRepository repository;

  @Test
  public void findAllTests(){
    log.info(repository.findAll()); 
  }
  
  @Test
  public void findByMemberMnoTests(){
    log.info(repository.findByMemberMno(24L)); 
  }

  @Test
  public void searchByKeywordTests(){
    log.info(repository.searchByKeyword("수정")); 
  }

  @Test
  public void findByIsDeletedFalseTests(){
    log.info(repository.findByIsDeletedFalse()); 
  }

  
}
