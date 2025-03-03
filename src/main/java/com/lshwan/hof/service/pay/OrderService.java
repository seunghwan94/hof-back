package com.lshwan.hof.service.pay;

import java.util.List;

import com.lshwan.hof.domain.dto.order.OrderDto;
import com.lshwan.hof.domain.dto.order.OrderHistoryDto;
import com.lshwan.hof.domain.entity.order.Order;

public interface OrderService {
  Order createOrder(OrderDto orderDto);
  Order getOrderById(Long orderNo);
  Order addDelivery(Long orderNo);
  List<Order> getOrdersByMember(Long mno);
  List<OrderHistoryDto> getOrderHistory(Long mno);
}
