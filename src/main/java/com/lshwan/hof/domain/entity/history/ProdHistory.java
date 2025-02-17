package com.lshwan.hof.domain.entity.history;

import com.lshwan.hof.domain.entity.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Getter
@Table(name = "tbl_history_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdHistory extends BaseEntityRegDate{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;  // 히스토리 번호 

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pno", nullable = false)
  private Prod product;  // 상품 번호 
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "admin_mno", nullable = false)
  private Member admin;  // 관리자 회원 번호 
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductAction action;  // 상품 변경 유형
  @Column(name = "prev_value")
  private String prevValue;  // 변경 전 값
  @Column(name = "new_value")
  private String newValue;  // 변경 후 값

  
  
  public enum ProductAction {
    PRICE_UPDATED, STOCK_UPDATED, CATEGORY_CHANGED, PRODUCT_DELETED
  }

}
