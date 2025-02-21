package com.lshwan.hof.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.payment.Pay;
import com.lshwan.hof.domain.entity.payment.Refund;
import com.lshwan.hof.repository.payment.PayRepository;
import com.lshwan.hof.repository.payment.RefundRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class RefundRepositoryTests {
  @Autowired
  private RefundRepository repository;
  @Autowired
  private PayRepository payRepository;

  @Test
  public void findAll(){
    log.info(repository.findAll()); 
  }

  @Test
  void testSaveRefund() {
    Optional<Pay> pay = payRepository.findById(15L);

    Refund refundTarget = repository.findByPay_No(15L);
    Refund refund = Refund.builder()
            .no(refundTarget.getNo())  
            .pay(pay.get())
            .reason("테스트 환불")
            .refundPrice(pay.get().getTotalPrice())
            .method(Refund.RefundMethod.카드)
            .status(true)
          .build();

    Refund savedRefund = repository.save(refund);
    assertNotNull(savedRefund.getNo());
    assertEquals("테스트 환불", savedRefund.getReason());
  }
}
