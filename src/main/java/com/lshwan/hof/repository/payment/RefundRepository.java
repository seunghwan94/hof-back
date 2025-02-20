package com.lshwan.hof.repository.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.payment.Refund;

public interface RefundRepository extends JpaRepository<Refund,Long>{
  List<Refund> findByStatus(boolean status);
  Refund findByPay_No(Long no);
}
