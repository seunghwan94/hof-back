package com.lshwan.hof.domain.dto.order;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
  private Long mno;
  private List<OrderItemDto> items;
  private int totalPrice;
}
