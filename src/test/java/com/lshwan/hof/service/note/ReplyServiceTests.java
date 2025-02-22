package com.lshwan.hof.service.note;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.note.Reply;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
  @Autowired
  private ReplyService replyService;

  @Test
  void addTests(){
    Long nno = 37L;
    Long mno = 24L;
    String content = "test";
    Long parentReplyId = null;

    Reply reply = replyService.add(nno, mno, content, parentReplyId);

    log.info(reply);
  }

  @Test
  void findListTests(){
    Long nno = 37L;
    log.info(replyService.findList(nno));
  }

  @Test
  void removeTests(){
    Long nno = 4L;
    replyService.remove(nno);
  }


}
