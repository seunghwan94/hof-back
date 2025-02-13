package com.lshwan.hof.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.payment.Pay;

public interface PayRepository extends JpaRepository<Pay,Long>{
  
}
