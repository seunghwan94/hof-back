package com.lshwan.hof.domain.dto.pay;

import com.lshwan.hof.domain.entity.payment.Pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayDto {
    private Long no;
    private Long orderNo;
    private String method;
    private String status;
    private int totalPrice;

    public PayDto(Pay pay) {
        this.no = pay.getNo();
        this.orderNo = pay.getOrder().getNo();
        this.method = pay.getMethod().name();
        this.status = pay.getStatus().name();
        this.totalPrice = pay.getTotalPrice();
    }
}
