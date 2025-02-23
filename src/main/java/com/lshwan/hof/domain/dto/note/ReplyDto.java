package com.lshwan.hof.domain.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
  private Long replyId;        // 댓글 ID
  private Long nno;            // 게시글 ID
  private Long mno;            // 작성자 ID
  private String memberName;   // 작성자 이름
  private String content;      // 댓글 내용
  private String createdAt;    // 작성일자
  private Long parentReplyId;  // 부모 댓글 ID (대댓글인 경우)
  private int depth;           // 댓글 깊이
  private boolean isDeleted;   // 삭제 여부
}
