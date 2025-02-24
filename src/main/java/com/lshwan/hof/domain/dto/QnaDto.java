package com.lshwan.hof.domain.dto;

import com.lshwan.hof.domain.entity.common.Qna;

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
public class QnaDto {
    private Long no;
    private String memberId;  // Member 대신 ID만 사용
    private Long pno;         // 등록 시 사용:
    private String prodTitle; // 조회 시 사용:
    private String content;
    private Qna.QnaStatus status;
    private Long parentNo;

    public QnaDto(Qna qna) {
        this.no = qna.getNo();
        this.memberId = qna.getMember().getId(); // ID만 저장
        this.prodTitle = qna.getPno().getTitle();
        this.content = qna.getContent();
        this.status = qna.getStatus();
        this.parentNo = (qna.getParentQna() != null) ? qna.getParentQna().getNo() : null;
    }
    
}
