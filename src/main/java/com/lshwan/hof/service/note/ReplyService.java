package com.lshwan.hof.service.note;

import java.util.List;

import com.lshwan.hof.domain.entity.note.Reply;

public interface ReplyService {
 // 댓글 작성
  Reply add(Long nno, Long mno, String content, Long parentReplyId);
  // 댓글 목록 조회
  List<Reply> findList(Long nno);
  // 댓글 삭제 (Soft Delete)
  void remove(Long replyId);
}
