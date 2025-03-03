package com.lshwan.hof.domain.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.email.EmailVerification;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class MemberDetail extends BaseEntity {

  // mdno 컬럼을 auto_increment로 설정하여 PRIMARY KEY 역할을 합니다.
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mdno;

  private String email;
  private boolean privacyConsent;
  private boolean marketingConsent;
  private boolean allowNotification;

  // 성별을 나타내는 enum
  @Enumerated(EnumType.STRING)
  private MemberGender gender;

  // Member 엔티티와 1:1 관계, mno는 외래키로 사용
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno")
  @JsonIgnore  // Member 직렬화에서 제외
  private Member member;

  // EmailVerification과 1:1 관계, cascade를 통해 MemberDetail 저장 시 EmailVerification도 함께 저장
  @OneToOne(mappedBy = "memberDetail", fetch = FetchType.LAZY)
  private EmailVerification emailVerification;

  // 성별을 나타내는 enum
  public enum MemberGender {
    MALE, FEMALE, OTHER;
  }
  
}
