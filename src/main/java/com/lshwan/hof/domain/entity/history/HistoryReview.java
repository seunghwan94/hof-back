package com.lshwan.hof.domain.entity.history;

import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.order.Review;

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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_history_review")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryReview extends BaseEntityRegDate{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_no", nullable = false)
  private Review review;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno", nullable = false)
  private Member member;

  @Enumerated(EnumType.STRING)
  @Column(name = "action", nullable = false)
  private ActionType action; 

  @Column(name = "prev_content")
  private String prevContent;

  @Column(name = "new_content")
  private String newContent;

  public enum ActionType {
      REVIEW_UPDATED, REVIEW_DELETED
  }
}
