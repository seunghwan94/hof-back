package com.lshwan.hof.domain.entity.member;

import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_remember")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Remember extends BaseEntity{
  
  @Id
  private Long mno;
  private String token;
  private String tokenExpired;
  private String userAgent;
  private String ipAddress;

  @OneToOne
  @JoinColumn(name = "mno", nullable = false)
  private Member member;
}
