package com.lshwan.hof.service.pay;

import com.lshwan.hof.domain.entity.payment.Refund;

public interface RefundService {
  Refund processRefund(Long pno, String reason);
  Refund getRefundByPayId(Long payMo);
}
