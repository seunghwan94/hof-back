package com.lshwan.hof.domain.entity.notice;


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

@Entity
@Table(name = "tbl_notice")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;
  
  @ManyToOne(fetch = FetchType.LAZY) // 회원 정보와 다대일 관계 설정
  @JoinColumn(name = "mno", nullable = false) // FK 설정
  private Member member;
  private String title;
  private String content;
  @Column(name = "click_url")
  private String clickUrl;
  @Column(name = "background_color", length = 7, nullable = false)
  private String backgroundColor;

}
