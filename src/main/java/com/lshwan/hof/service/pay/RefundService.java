package com.lshwan.hof.service.pay;

import com.lshwan.hof.domain.entity.payment.Refund;

public interface RefundService {
  Refund processRefund(Long payNo, String reason, Refund.RefundMethod method, int refundPrice);
}
