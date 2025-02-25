package com.lshwan.hof.controller.main;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.note.ReplyDto;
import com.lshwan.hof.service.note.ReplyService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("main/reply")
public class ReplyController {
  private final ReplyService replyService;

  // 댓글 작성
  @PostMapping
  public ResponseEntity<?> createReply(@RequestBody ReplyDto replyDto) {
    ReplyDto createdReply = replyService.add(replyDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdReply);
  }

  // 댓글 목록 조회
  @GetMapping("/note/{nno}")
  public ResponseEntity<List<?>> getRepliesByNote(@PathVariable(name = "nno") Long nno) {
    List<ReplyDto> replies = replyService.findList(nno);
    return ResponseEntity.ok(replies);
  }

  // 댓글 삭제 (Soft Delete)
  @DeleteMapping("/{replyId}")
  public ResponseEntity<Void> deleteReply(@PathVariable(name = "replyId") Long replyId) {
    replyService.remove(replyId);
    return ResponseEntity.noContent().build();
  }
}
