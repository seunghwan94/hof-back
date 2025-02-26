package com.lshwan.hof.domain.entity.social;

import com.lshwan.hof.domain.entity.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.member.Member;

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
import lombok.Setter;

@Entity
@Table(name = "tbl_social")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Social extends BaseEntityRegDate{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  private String id;
  private String email;
  private String name;

  @Enumerated(EnumType.STRING)
  private SocialType type;

  public enum SocialType {
    GOOGLE, GITHUB, KAKAO, NAVER
  }

  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "mno", insertable = false, updatable = false)
  private Member member;
}
