package com.lshwan.hof.domain.dto.order;

import java.util.List;

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
public class OrderDto {
  private Long mno;
  private List<OrderItemDto> items;
  private int totalPrice;
}
