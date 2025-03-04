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

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("main/reply")
public class ReplyController {
  private final ReplyService replyService;

  // 댓글 작성
  @PostMapping
    @Operation(summary = "댓글 달기 API", description = "댓글 Insert 를 합니다")
  public ResponseEntity<?> createReply(@RequestBody ReplyDto replyDto) {
    ReplyDto createdReply = replyService.add(replyDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdReply);
  }

  // 댓글 목록 조회
  @GetMapping("/note/{nno}")
  @Operation(summary = "게시글의 댓글 리스트 조회", description = "게시글의 모든 댓글을 조회합니다")
  public ResponseEntity<List<?>> getRepliesByNote(@PathVariable(name = "nno") Long nno) {
    List<ReplyDto> replies = replyService.findList(nno);
    return ResponseEntity.ok(replies);
  }

  // 댓글 삭제 (Soft Delete)
  @DeleteMapping("/{replyId}")
  @Operation(summary = "댓글삭제 API", description = "자신의 댓글이거나 관리자 가 댓글을삭제합니다")
  public ResponseEntity<Void> deleteReply(@PathVariable(name = "replyId") Long replyId) {
    replyService.remove(replyId);
    return ResponseEntity.noContent().build();
  }
}
