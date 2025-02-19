package com.lshwan.hof.service.pay;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.order.Order;
import com.lshwan.hof.domain.entity.payment.Pay;
import com.lshwan.hof.repository.order.OrderRepository;
import com.lshwan.hof.repository.payment.PayRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class PayServiceImpl implements PayService{
    private final PayRepository payRepository;
    private final OrderRepository orderRepository;

    /**
     * 1️⃣ 결제 요청
     */
    @Transactional
    public Pay requestPayment(Long orderNo, Pay.PaymentMethod method, int totalPrice) {
        Order order = orderRepository.findById(orderNo)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        Pay pay = Pay.builder()
                .order(order)
                .method(method)
                .status(Pay.PaymentStatus.실패) // 기본값은 실패로 설정
                .totalPrice(totalPrice)
                .build();

        return payRepository.save(pay);
    }

    /**
     * 2️⃣ 결제 검증 (DB 내 결제 정보와 실제 결제 정보 비교)
     */
    public boolean verifyPayment(Long payNo, int verifiedAmount) {
        Pay pay = payRepository.findById(payNo)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        return pay.getTotalPrice() == verifiedAmount; // 검증 성공 여부 반환
    }

    /**
     * 3️⃣ 결제 성공 처리
     */
    @Transactional
    public Pay successPayment(Long payNo) {
        Pay pay = payRepository.findById(payNo)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        pay = Pay.builder()
                .no(pay.getNo())
                .order(pay.getOrder())
                .method(pay.getMethod())
                .status(Pay.PaymentStatus.완료) // ✅ 결제 상태 업데이트
                .totalPrice(pay.getTotalPrice())
                .build();

        return payRepository.save(pay);
    }

    /**
     * 4️⃣ 결제 실패 처리
     */
    @Transactional
    public Pay failPayment(Long payNo) {
        Pay pay = payRepository.findById(payNo)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        pay = Pay.builder()
                .no(pay.getNo())
                .order(pay.getOrder())
                .method(pay.getMethod())
                .status(Pay.PaymentStatus.실패) // ✅ 결제 실패 처리
                .totalPrice(pay.getTotalPrice())
                .build();

        return payRepository.save(pay);
    }
  
    /**
     * 주문 번호(orderNo)를 기준으로 결제 정보 조회
     */
    public Pay getPaymentByOrder(Long orderNo) {
        return payRepository.findByOrder_No(orderNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문에 대한 결제 정보가 없습니다."));
    }
}
