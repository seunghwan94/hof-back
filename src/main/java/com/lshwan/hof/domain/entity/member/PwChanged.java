package com.lshwan.hof.domain.entity.member;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tbl_pw_changed")
public class PwChanged {

  @Id
  @CreatedDate
  private Long mno;
  private String oldPw;
  private LocalDateTime regDate;


  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "mno", insertable = false, updatable = false)
  private Member member;

  
}

