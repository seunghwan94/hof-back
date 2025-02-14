package com.lshwan.hof.domain.entity.member;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;



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
@Table(name = "tbl_pw_changed")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
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

