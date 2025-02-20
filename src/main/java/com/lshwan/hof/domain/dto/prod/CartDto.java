package com.lshwan.hof.domain.dto.prod;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDto {
  private Long cartNo;
  private Long mno;
  private Long pno;
  private String title;
  private int price;
  private int count;
  private List<String> imageUrls; 
  private List<ProdOptionDto> options; 

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class ProdOptionDto {
      private Long optionNo;
      private String type;
      private String value;
      private int addPrice;
      private int stock;
  }

}
