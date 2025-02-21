package com.lshwan.hof.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lshwan.hof.domain.entity.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
  List<OrderItem> findByOrder_No(Long orderNo); // 특정 주문의 상품 조회
}
