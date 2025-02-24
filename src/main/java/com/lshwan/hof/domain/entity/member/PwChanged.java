package com.lshwan.hof.domain.entity.member;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lshwan.hof.domain.entity.BaseEntityRegDate;

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
@Table(name = "tbl_pw_changed")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PwChanged extends BaseEntityRegDate{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;
  
  private String oldPw;
  private LocalDateTime regDate;

  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "mno")
  @JsonIgnore
  private Member member;
  
}

