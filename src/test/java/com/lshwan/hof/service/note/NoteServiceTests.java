package com.lshwan.hof.service.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.note.NoteDto;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class NoteServiceTests {
  @Autowired
  private NoteService service;

  @Test
  public void addTest(){
    // get
    NoteDto noteDto = NoteDto.builder()
                        .mno(24L)
                        .title("테스트제목")
                        .content("테스트 내용")
                      .build();
    // when
    NoteDto note = service.add(noteDto);
    // then
    assertNotNull(note);
    assertNotNull(note.getNno());
  }

  // member 문제로 오류
  @Test
  public void findByTest(){
    // given
    NoteDto noteDto = NoteDto.builder()
                        .mno(24L)
                        .title("조회 테스트")
                        .content("조회 테스트 내용")
                      .build();
    NoteDto savedNote = service.add(noteDto);
    Long nno = savedNote.getNno();
    // when
    NoteDto foundNote = service.findBy(nno);
    // then
    assertNotNull(foundNote);
    assertEquals(nno, foundNote.getNno());
  }

  // member 문제로 오류
  @Test
  public void findList(){
    // when
    List<NoteDto> list = service.findList();

    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void modify(){
    // target
    NoteDto noteDto = NoteDto.builder()
        .mno(24L)
        .title("테스트제목")
        .content("테스트 내용")
      .build();
    NoteDto note = service.add(noteDto);
    // given
    Long nno = note.getMno();
    String title = "수정된테스트제목";
    String content = "수정된테스트내용";
    // when
    noteDto = service.modify(nno, title, content);
    // then
    assertNotNull(noteDto);
  }

  @Test
  public void remove(){
    // target
    NoteDto noteDto = NoteDto.builder()
        .mno(24L)
        .title("테스트제목")
        .content("테스트 내용")
      .build();
    NoteDto note = service.add(noteDto);
    // given
    Long nno = note.getMno();
    // when
    service.remove(nno);
  }
}
