package com.lshwan.hof.domain.entity.common;

import com.lshwan.hof.domain.entity.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_qna")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Qna {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @ManyToOne(fetch = FetchType.LAZY)  // 문의한 회원 (FK)
  @JoinColumn(name = "mno", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)  // 부모 문의 (Self-referencing 관계)
  @JoinColumn(name = "parent_no")
  private Qna parentQna;

  private int depth;  // 같은 답글 아래에서의 정렬 순서

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private QnaStatus status;  // 처리 상태 (ENUM)

  public enum QnaStatus {
    처리전, 처리후
  }
}
