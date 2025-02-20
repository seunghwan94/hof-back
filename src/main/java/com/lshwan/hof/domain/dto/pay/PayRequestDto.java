package com.lshwan.hof.domain.dto.pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRequestDto {
    private Long orderNo;
    private String method;
    private int totalPrice;
    private String impUid;
}
