package com.lshwan.hof.domain.entity.prod;


import com.lshwan.hof.domain.entity.BaseEntity;


import jakarta.persistence.Column;
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
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tbl_prod_option_map")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdOptionMap extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no; // 옵션-상품 매핑 ID (Primary Key)

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pno", nullable = false)
  private Prod prod; // 상품 번호 (Foreign Key)

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_no", nullable = false)
  private ProdOption option; // 옵션 번호 (Foreign Key)

  @Default
  @Column(nullable = false)
  private int stock = 0; // 옵션 별 재고

}
