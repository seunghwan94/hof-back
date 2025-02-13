package com.lshwan.hof.domain.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_member_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberDetail extends BaseEntity{

  @Id
  private Long mno;
  private String email;
  private boolean privacyConsent;
  private boolean marketingConsent;
  private boolean allowNotification;

  @Enumerated(EnumType.STRING)
  private MemberGender gender;

  @OneToOne
  @JoinColumn(name = "mno", nullable = false)
   @JsonIgnore  // 🚀 MemberDetail에서 Member 직렬화 제외
  private Member member;

  public enum MemberGender {
    FEMALE, MALE, OTHER
  }
}
