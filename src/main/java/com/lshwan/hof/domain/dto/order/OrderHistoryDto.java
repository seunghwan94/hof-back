package com.lshwan.hof.domain.dto.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryDto {
    private Long payNo;            // 결제 번호
    private Long orderNo;          // 주문 번호
    private String productName;    // 상품명
    private int totalPrice;        // 총 결제 금액
    private String deliveryStatus; // 배송 상태
    private String orderDate;      // 주문 날짜 (yyyy/MM/dd 포맷)
    private boolean isRefunded;    // 환불 여부 (true면 환불됨)
}

