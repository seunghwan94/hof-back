package com.lshwan.hof.service.pay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.payment.Refund;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Transactional
@Log4j2
public class RefundServiceTests {
  @Autowired
  private RefundService refundService;

  @Test
  @DisplayName("환불 한 제품 조회")
  void testGetRefundByPayId_NotFound() {
    Refund refund = null;
    try{
      refund = refundService.processRefund(15L, "그냥");
    }catch(Exception e){
      assertEquals("이미 환불이 완료된 결제입니다.", e.getMessage());
    }
    assertNull(refund);
  }

  // @Test
  // @DisplayName("환불 해야되는 제품")
  // void testGetRefundByPayId_Success() {
  //   Refund refund = refundService.processRefund(39L, "그냥");
  //   assertNotNull(refund);
  // }
}
