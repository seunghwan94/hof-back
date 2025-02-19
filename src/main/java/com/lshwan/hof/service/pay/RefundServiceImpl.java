package com.lshwan.hof.service.pay;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.payment.Pay;
import com.lshwan.hof.domain.entity.payment.Refund;
import com.lshwan.hof.repository.payment.PayRepository;
import com.lshwan.hof.repository.payment.RefundRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class RefundServiceImpl implements RefundService{
        private final RefundRepository refundRepository;
        private final PayRepository payRepository;

        /**
         * 5️⃣ 환불 처리
         */
        @Transactional
        public Refund processRefund(Long payNo, String reason, Refund.RefundMethod method, int refundPrice) {
                Pay pay = payRepository.findById(payNo)
                        .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

                Refund refund = Refund.builder()
                        .pay(pay)
                        .reason(reason)
                        .method(method)
                        .status(true) // ✅ 환불 완료
                        .refundPrice(refundPrice)
                        .refundDate(LocalDateTime.now())
                        .build();

                return refundRepository.save(refund);
        }
}
