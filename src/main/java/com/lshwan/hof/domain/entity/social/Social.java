package com.lshwan.hof.domain.entity.social;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.lshwan.hof.domain.entity.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_social")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Social {
  
  @Id
  @CreatedDate
  private Long mno;
  private String id;
  private String email;
  private String name;
  private LocalDateTime regDate;

  @Enumerated(EnumType.STRING)
  private SocialType type;

  public enum SocialType {
    GOOGLE, GITHUB, KAKAO, NAVER
  }

  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "mno", insertable = false, updatable = false)
  private Member member;
}
