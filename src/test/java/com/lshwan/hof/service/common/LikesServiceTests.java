package com.lshwan.hof.service.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.common.Likes;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class LikesServiceTests {
  @Autowired
  private LikesService service;

  @Test
  void addTests(){
    Long mno = 24L;
    Long targetNo = 37L;
    Likes.TargetType targetType = Likes.TargetType.NOTE;

    service.add(mno, targetNo, targetType);
  }
  @Test
  void remove(){
    Long mno = 24L;
    Long targetNo = 1L;
    Likes.TargetType targetType = Likes.TargetType.NOTE;

    service.remove(mno, targetNo, targetType);
  }
  @Test
  void countLikes(){
    Long targetNo = 37L;
    Likes.TargetType targetType = Likes.TargetType.NOTE;
    service.countLikes(targetNo, targetType);
  }
}
