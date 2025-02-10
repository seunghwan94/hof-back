package com.lshwan.hof.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{
  
}
