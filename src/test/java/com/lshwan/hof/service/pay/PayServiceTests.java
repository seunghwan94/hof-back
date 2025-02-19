package com.lshwan.hof.service.pay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.payment.Pay;
import com.lshwan.hof.domain.entity.payment.Pay.PaymentMethod;
import com.lshwan.hof.domain.entity.payment.Pay.PaymentStatus;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class PayServiceTests {
      @Autowired
    private PayServiceImpl payService;

    /**
     * 1️⃣ 결제 요청 테스트
     */
    @Test
    public void 결제_요청_테스트() {
        // Given (결제 요청 데이터 준비)
        Long orderNo = 4L; // 실제 존재하는 주문 번호 사용
        PaymentMethod method = PaymentMethod.카드;
        int totalPrice = 50000;

        // When (결제 요청)
        Pay savedPay = payService.requestPayment(orderNo, method, totalPrice);

        // Then (검증)
        assertNotNull(savedPay);
        assertTrue(savedPay.getNo() > 0);
        assertEquals(PaymentStatus.실패, savedPay.getStatus()); // 기본값 실패
        assertEquals(totalPrice, savedPay.getTotalPrice());
        log.info("결제 요청 완료, 결제 번호: {}", savedPay.getNo());
    }

    /**
     * 2️⃣ 결제 검증 테스트
     */
    @Test
    public void 결제_검증_테스트() {
        // Given (결제 요청)
        Long orderNo = 4L;
        Pay savedPay = payService.requestPayment(orderNo, PaymentMethod.이체, 30000);

        // When (결제 금액 검증)
        boolean isValid = payService.verifyPayment(savedPay.getNo(), 30000);

        // Then (검증)
        assertTrue(isValid);
        log.info("결제 검증 완료, 검증 결과: {}", isValid);
    }

    /**
     * 3️⃣ 결제 성공 처리 테스트
     */
    @Test
    public void 결제_성공_테스트() {
        // Given (결제 요청)
        Long orderNo = 4L;
        Pay savedPay = payService.requestPayment(orderNo, PaymentMethod.카드, 45000);

        // When (결제 성공 처리)
        Pay updatedPay = payService.successPayment(savedPay.getNo());

        // Then (검증)
        assertNotNull(updatedPay);
        assertEquals(PaymentStatus.완료, updatedPay.getStatus()); // 상태가 완료로 변경
        log.info("결제 성공 처리 완료, 결제 상태: {}", updatedPay.getStatus());
    }

    /**
     * 4️⃣ 결제 실패 처리 테스트
     */
    @Test
    public void 결제_실패_테스트() {
        // Given (결제 요청)
        Long orderNo = 4L;
        Pay savedPay = payService.requestPayment(orderNo, PaymentMethod.무통장, 60000);

        // When (결제 실패 처리)
        Pay updatedPay = payService.failPayment(savedPay.getNo());

        // Then (검증)
        assertNotNull(updatedPay);
        assertEquals(PaymentStatus.실패, updatedPay.getStatus()); // 상태 유지
        log.info("결제 실패 처리 완료, 결제 상태: {}", updatedPay.getStatus());
    }
}
