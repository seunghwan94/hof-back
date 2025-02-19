package com.lshwan.hof.repository.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.payment.Pay;

public interface PayRepository extends JpaRepository<Pay,Long>{
  Optional<Pay> findByOrder_No(Long orderNo);
}
