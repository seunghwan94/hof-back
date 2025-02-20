package com.lshwan.hof.domain.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.email.EmailVerification;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_member_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
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
  @MapsId
  @JoinColumn(name = "mno", nullable = false)
   @JsonIgnore  // 🚀 MemberDetail에서 Member 직렬화 제외
  private Member member;

  @OneToOne(mappedBy = "memberDetail", cascade = CascadeType.ALL)
  private EmailVerification emailVerification;

  public enum MemberGender {
    FEMALE, MALE, OTHER
  }
}
