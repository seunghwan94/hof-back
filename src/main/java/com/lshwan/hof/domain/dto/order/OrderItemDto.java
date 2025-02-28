package com.lshwan.hof.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
  private Long pno;
  private Long optionNo;
  private int count;
  private int basePrice;
  private int subtotalPrice;
}
