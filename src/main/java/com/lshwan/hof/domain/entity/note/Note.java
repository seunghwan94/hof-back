package com.lshwan.hof.domain.entity.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Builder.Default;

@Entity
@Table(name = "tbl_note")  // 테이블 명 명시
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Note extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long nno;

  @ManyToOne(fetch = FetchType.LAZY) // 회원 정보와 다대일 관계 설정
  @JoinColumn(name = "mno", nullable = false) // FK 설정
  @JsonIgnore
  private Member member;


  @Column(length = 255, nullable = false)
  private String title;
  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;
  
  @Default
  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = false;

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
  public void setIsDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
}
}
