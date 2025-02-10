package com.lshwan.hof.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
  
}
