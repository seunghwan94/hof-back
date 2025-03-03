package com.lshwan.hof.service.pay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.pay.AdminRepundDto;
import com.lshwan.hof.domain.entity.order.OrderItem;
import com.lshwan.hof.domain.entity.payment.Pay;
import com.lshwan.hof.domain.entity.payment.Refund;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.repository.order.OrderItemRepository;
import com.lshwan.hof.repository.payment.PayRepository;
import com.lshwan.hof.repository.payment.RefundRepository;
import com.lshwan.hof.repository.prod.ProdRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Transactional
@Log4j2
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final PayRepository payRepository;
    private final OrderItemRepository orderItemRepository;  // 주문 아이템 접근용
    private final ProdRepository prodRepository;            // 상품 접근용
    private final IamportService iamportService;

    /**
     * 환불 요청 처리
     */
    @Override
    public Refund processRefund(Long pay_no, String reason) {
        // 1. 결제 정보 조회
        Pay pay = payRepository.findById(pay_no)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        // 2. 결제 상태 검증
        if (pay.getStatus() != Pay.PaymentStatus.완료) {
            throw new IllegalStateException("결제가 완료된 상태에서만 환불할 수 있습니다.");
        }

        // 3. 기존 환불 여부 확인
        Refund existingRefund = refundRepository.findByPay_No(pay_no);
        if (existingRefund != null && existingRefund.isStatus()) {
          throw new IllegalStateException("이미 환불이 완료된 결제입니다.");
        }

        Map<String, Object> refundResponse = iamportService.cancelPayment(pay.getImpUid(), reason);

        String status = (String) refundResponse.get("status");
        if ("cancelled".equals(status)) {
            log.info("환불 성공: impUid={}", pay.getImpUid());
        } else {
            log.error("환불 실패: impUid={}, 응답={}", pay.getImpUid(), refundResponse);
        }

        // 4. 환불 객체 생성
        Refund refund = Refund.builder()
                .pay(pay)
                .reason(reason)
                .refundPrice(pay.getTotalPrice())
                .method(Refund.RefundMethod.카드)
                .status(true) // 환불 성공
                .refundDate(LocalDateTime.now())
              .build();

        // 5. 환불 저장
        Refund savedRefund = refundRepository.save(refund);

        // 6. 결제 상태 업데이트 (환불로 변경)
        pay = Pay.builder()
                .no(pay.getNo())
                .impUid(pay.getImpUid())
                .order(pay.getOrder())
                .method(pay.getMethod())
                .totalPrice(pay.getTotalPrice())
              .build();
        payRepository.save(pay);

        // 7. 상품 재고 복구
        restoreProductStock(pay.getOrder().getNo());

        return savedRefund;
    }

    /**
     * 주문에 포함된 상품들의 재고를 환불 수량만큼 복구
     */
    public void restoreProductStock(Long orderNo) {
      List<OrderItem> orderItems = orderItemRepository.findByOrder_No(orderNo);

      for (OrderItem item : orderItems) {
        Prod product = item.getProd();
        int restoreQuantity = item.getCount();

        product.setStock(product.getStock() + restoreQuantity); // 재고 복구
        prodRepository.save(product);

        log.info("상품 [{}] 재고 복구: {} -> {}", product.getPno(), product.getStock() - restoreQuantity, product.getStock());
      }
    }

    /**
     * 결제 ID로 환불 정보 조회
    */
    @Override
    public Refund getRefundByPayId(Long payId) {
      Refund refund = refundRepository.findByPay_No(payId);
      if (refund == null) {
        try {
          throw new Exception("해당 결제에 대한 환불 정보가 없습니다.");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return refund;
    }
    @Override
    public List<AdminRepundDto> getAllRefundsByAdmin() {
      List<Refund> refunds = refundRepository.findAll();
        return refunds.stream()
                .map(AdminRepundDto::fromEntity)
                .collect(Collectors.toList());
    }
}
