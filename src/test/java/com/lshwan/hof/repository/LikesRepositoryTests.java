package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.common.Likes;
import com.lshwan.hof.repository.common.LikesRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class LikesRepositoryTests {

  @Autowired
  private LikesRepository repository;

  @Test
  public void findAll(){
    repository.findAll();
  }
  @Test
  public void countByTargetTests(){
    log.info(repository.countByTarget(34L,Likes.TargetType.NOTE));
  }
  @Test
  public void existsByIdTests(){
    Long memberId = 24L;  
    Long targetNo = 34L; 
    Likes.TargetType targetType = Likes.TargetType.NOTE;

    Likes.LikesId likesId = new Likes.LikesId(memberId, targetNo, targetType);

    log.info(repository.existsById(likesId));
  }
  @Test
  public void deleteByTargetTests(){
    repository.deleteByTarget(34L,Likes.TargetType.NOTE);
  }
}