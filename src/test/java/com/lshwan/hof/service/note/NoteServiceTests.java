package com.lshwan.hof.service.note;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.note.Note;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class NoteServiceTests {
  @Autowired
  private NoteService service;

   @Test
  public void addTest(){
    // // get
    // Long mno = 24L;
    // String title = "테스트제목";
    // String content = "테스트 내용";

    // // when
    // Note note = service.add(mno, title, content);
    // log.info(note);
    // // then
    // assertNotNull(note);
  }

  @Test
  public void findBy(){
    // target
    Long nno = 1L;
    // get & when
    Note note = service.findBy(nno);
    log.info("prodCategoryCno : {}", note.getNno());
    // then
    assertNotNull(note.getNno());
  }

  @Test
  public void findList(){
    // when
    List<Note> list = service.findList();

    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  // @Transactional
  public void modify(){
    // given
    Long nno = 32L;
    String title = "수정된테스트제목";
    String content = "수정된테스트내용";

    // when
    Note note = service.modify(nno, title, content);

    // then
    assertNotNull(note);
  }

  @Test
  public void remove(){
    // given
    Long nno = 37L;

    // when
    service.remove(nno);

  }
}
