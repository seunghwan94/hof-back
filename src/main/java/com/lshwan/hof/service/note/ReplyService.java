package com.lshwan.hof.service.note;

import java.util.List;

import com.lshwan.hof.domain.dto.note.ReplyDto;

public interface ReplyService {
 // 댓글 작성
  ReplyDto add(ReplyDto replyDto);
  // 댓글 목록 조회
  List<ReplyDto> findList(Long nno);
  // 댓글 삭제 (Soft Delete)
  void remove(Long replyId);
}
