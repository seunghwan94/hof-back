package com.lshwan.hof.domain.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  private String token;
  private String tokenExpired;
  private String userAgent;
  private String ipAddress;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno")
  @JsonIgnore
  private Member member;
}
