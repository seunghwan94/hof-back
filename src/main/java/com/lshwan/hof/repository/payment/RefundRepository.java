package com.lshwan.hof.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.payment.Refund;

public interface RefundRepository extends JpaRepository<Refund,Long>{
  
}
