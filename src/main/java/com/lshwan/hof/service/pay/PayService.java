package com.lshwan.hof.service.pay;

import java.util.List;

import com.lshwan.hof.domain.dto.pay.AdminPayDto;
import com.lshwan.hof.domain.dto.pay.PayDto;
import com.lshwan.hof.domain.entity.payment.Pay;

public interface PayService {
  Pay requestPayment(Long orderNo, Pay.PaymentMethod method, int totalPrice, String impUid);

  boolean verifyPayment(Long payNo, int verifiedAmount);

  Pay successPayment(Long payNo);

  Pay failPayment(Long payNo);

  Pay getPaymentByOrder(Long orderNo);
  
  List<AdminPayDto> findList();
}
