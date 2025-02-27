package com.lshwan.hof.domain.dto.common;

import com.lshwan.hof.domain.entity.prod.view.ProdView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeProdDto {
  private Long pno; // 상품 번호
  private String title; // 상품명
  private String imageUrl; // 대표 이미지 URL
  private int price; // 가격
  private double avgStar; // 평균 별점
  private long reviewCount; // 리뷰 개수


  public static LikeProdDto fromEntity(ProdView prodView) {
      if (prodView == null) return null;

      return LikeProdDto.builder()
                .pno(prodView.getPno())
                .title(prodView.getTitle())
                .imageUrl(prodView.getImageUrl())
                .price(prodView.getPrice())
                .avgStar(prodView.getAvgStar())
                .reviewCount(prodView.getReviewCount())
              .build();
  }
}