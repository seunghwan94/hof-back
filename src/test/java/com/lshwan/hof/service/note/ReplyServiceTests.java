package com.lshwan.hof.service.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.note.ReplyDto;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ReplyServiceTests {
  @Autowired
  private ReplyService replyService;

  @Test
  void addTests(){
    // target
    Long nno = 25L;
    Long mno = 24L;
    String content = "test";
    Long parentReplyId = null;
    // given
    ReplyDto reply = ReplyDto.builder()
        .nno(nno)
        .mno(mno)
        .content(content)
        .parentReplyId(parentReplyId)
      .build();
    // when
    ReplyDto replyDto = replyService.add(reply);
    // then
    assertNotNull(replyDto);
  }

  @Test
  void findListTests(){
    // target
    Long nno = 25L;
    // when
    List<ReplyDto> replies = replyService.findList(nno);
    // then 
    assertNotNull(replies);
  }

  @Test
  void removeTests(){
    // target
    Long nno = 25L;
    Long mno = 24L;
    String content = "test";
    Long parentReplyId = null;
    // given
    ReplyDto reply = ReplyDto.builder()
        .nno(nno)
        .mno(mno)
        .content(content)
        .parentReplyId(parentReplyId)
      .build();
    // when
    ReplyDto replyDto = replyService.add(reply);
    // then
    replyService.remove(replyDto.getNno());
    assertFalse(replyDto.isDeleted()); 
  }

}
