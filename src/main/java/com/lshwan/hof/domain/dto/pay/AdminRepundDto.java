package com.lshwan.hof.domain.dto.pay;

import java.time.LocalDateTime;


import com.lshwan.hof.domain.entity.payment.Refund.RefundMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRepundDto {
  private Long no;
    private Long payNo;   // 환불된 결제 ID
    private String reason;
    private boolean status;
    private int refundPrice;
    private RefundMethod method;
    private LocalDateTime refundDate;
    private String memberId;

    public static AdminRepundDto fromEntity(com.lshwan.hof.domain.entity.payment.Refund refund) {
        return AdminRepundDto.builder()
                .no(refund.getNo())
                .payNo(refund.getPay().getNo())
                .reason(refund.getReason())
                .status(refund.isStatus())
                .refundPrice(refund.getRefundPrice())
                .method(refund.getMethod())
                .refundDate(refund.getRefundDate())
                .memberId(refund.getPay().getOrder().getMember().getId())
                .build();
    }
}
