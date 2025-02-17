package com.lshwan.hof.domain.entity.prod.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "vw_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdView {
  @Id
  private Long pno; // 상품 번호 (기본키 역할)
  private Long cno;

  private String title;  // 상품 제목
  private int price;     // 가격
  private String imageUrl; // 대표 이미지 URL
  private double avgStar; // 평균 별점
  private long reviewCount; // 리뷰 개수
  
}
