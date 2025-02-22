package com.lshwan.hof.controller.main;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.entity.note.Reply;
import com.lshwan.hof.service.note.ReplyService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("main/reply")
public class ReplyController {
  private final ReplyService replyService;

  // 댓글 작성
  @PostMapping
  public ResponseEntity<Reply> createReply(@RequestParam Long nno,
                                            @RequestParam Long mno,
                                            @RequestParam String content,
                                            @RequestParam(required = false) Long parentReplyId) {
      Reply reply = replyService.add(nno, mno, content, parentReplyId);
      return ResponseEntity.status(HttpStatus.CREATED).body(reply);
  }

  // 댓글 목록 조회
  @GetMapping("/note/{nno}")
  public ResponseEntity<List<Reply>> getRepliesByNote(@PathVariable Long nno) {
      List<Reply> replies = replyService.findList(nno);
      return ResponseEntity.ok(replies);
  }

  // 댓글 삭제 (Soft Delete)
  @DeleteMapping("/{replyId}")
  public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
      replyService.remove(replyId);
      return ResponseEntity.noContent().build();
  }
}
