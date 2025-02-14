package com.lshwan.hof.domain.entity.social;


import java.time.LocalDateTime;

import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.member.Member;

import jakarta.persistence.Entity;
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
@Table(name = "tbl_mfa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MFA extends BaseEntity{
  
  @Id
  private Long mno;
  private boolean isEnabled;
  private String secretKey;
  private String backupCodes;
  private LocalDateTime lastVerifiedAt;
  private String lastLoginIp;

  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "mno", insertable = false, updatable = false)
  private Member member;
}
