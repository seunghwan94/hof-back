package com.lshwan.hof.service.pay;

import java.util.List;

import com.lshwan.hof.domain.dto.pay.AdminRepundDto;
import com.lshwan.hof.domain.entity.payment.Refund;

public interface RefundService {
  Refund processRefund(Long pno, String reason);
  Refund getRefundByPayId(Long pno);

  List<AdminRepundDto> getAllRefundsByAdmin();
}
