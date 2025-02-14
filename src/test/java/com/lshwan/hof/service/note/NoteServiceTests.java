package com.lshwan.hof.service.note;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.note.Note;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class NoteServiceTests {
  @Autowired
  private NoteService service;

   @Test
  public void addTest(){
    // get
    Note note = Note.builder()
      .member(Member.builder().mno(5L).build())
      .title("테스트제목")
      .content("테스트 내용")
      .build();
    // when
    Long cno = service.add(note);
    // then
    assertTrue(cno > 0);
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
    Note note = Note.builder()
        .member(Member.builder().mno(5L).build())
        .title("테스트제목")
        .content("테스트 내용")
      .build();
    Long nno = service.add(note);
    assertNotNull(nno);

    // when
    Note modinote = Note.builder()
        .nno(nno)
        .member(Member.builder().mno(5L).build())
        .title("수정된테스트제목")
        .content("수정된테스트내용")
      .build();
    Long modinno = service.modify(modinote);

    // then
    assertNotNull(modinno);
    Note note2 = service.findBy(modinno);
    assertNotNull(note2);
    assertTrue(note2.getNno() == nno);
  }

  @Test
  public void remove(){
    // given
    Note note = Note.builder()
    .member(Member.builder().mno(5L).build())
    .title("수정된테스트제목")
    .content("수정된테스트내용")
      .build();
    Long nno = service.add(note);
    assertNotNull(nno);

    // when
    boolean isRemove = service.remove(nno);

    // then
    assertTrue(isRemove);
  }
}
