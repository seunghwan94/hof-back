package com.lshwan.hof.service.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.common.LikeDto;
import com.lshwan.hof.domain.dto.common.LikeProdDto;
import com.lshwan.hof.domain.entity.common.Likes;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.note.NoteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class LikesServiceTests {
  @Autowired
  private LikesService service;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private NoteRepository noteRepository;

  @Test
  void addTests(){
    // target
    Long mno = 24L;
    Long targetNo = 40L;
    // get
    Optional<Member> member = memberRepository.findById(mno);
    Optional<Note> note = noteRepository.findById(targetNo);
    assertNotNull(member);
    assertNotNull(note);
    Likes.TargetType targetType = Likes.TargetType.NOTE;
    // when
    LikeDto likeDto = service.add(member.get().getMno(), note.get().getNno(), targetType);
    // then
    assertNotNull(likeDto);
  }

  @Test
  void remove(){
    // target
    Long mno = 24L;
    Long targetNo = 40L;
    Likes.TargetType targetType = Likes.TargetType.NOTE;
    // given
    service.add(mno, targetNo, targetType);
    // when 
    service.remove(mno, targetNo, targetType);
    // then
    boolean exists = service.findBy(mno, targetNo, targetType);
    assertFalse(exists);
  }

  @Test
  void countLikes(){
    // target
    Long targetNo = 40L;
    // given
    Likes.TargetType targetType = Likes.TargetType.NOTE;
    service.add(25L, targetNo, targetType);
    service.add(26L, targetNo, targetType);
    service.add(27L, targetNo, targetType);
    // when
    long likeCount = service.countLikes(targetNo, targetType);
    // then 
    assertEquals(3, likeCount);  
  }

  @Test
  public void getLikedProductsTest() {
    // target
    Long mno = 24L;
    // given
    service.add(mno,408L, Likes.TargetType.PROD);
    service.add(mno, 409L, Likes.TargetType.PROD);
    service.add(mno, 410L, Likes.TargetType.PROD);
    // when
    List<LikeProdDto> likedProducts = service.getLikedProducts(mno);
    log.info(likedProducts);
    // then
    assertNotNull(likedProducts);
  }

}
