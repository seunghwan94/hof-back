package com.lshwan.hof.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDto {
  private Long pno;
  private int count;
  private int basePrice;
  private int subtotalPrice;
}
