package com.lshwan.hof.domain.entity.member;

import com.lshwan.hof.domain.entity.BaseEntity;

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
@Table(name = "tbl_addr")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberAddr extends BaseEntity{

  @Id
  private Long mno;
  private String zipcode;
  private String roadAddr;
  private String detailAddr;
  private boolean is_default;  
  
  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "mno", insertable = false, updatable = false)
  private Member member;
}