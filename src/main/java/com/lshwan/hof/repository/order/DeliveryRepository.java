package com.lshwan.hof.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.order.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery,Long>{
  
}
