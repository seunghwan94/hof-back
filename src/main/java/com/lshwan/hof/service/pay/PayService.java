package com.lshwan.hof.service.pay;

import com.lshwan.hof.domain.entity.payment.Pay;

public interface PayService {
  Pay requestPayment(Long orderNo, Pay.PaymentMethod method, int totalPrice);

  boolean verifyPayment(Long payNo, int verifiedAmount);

  Pay successPayment(Long payNo);

  Pay failPayment(Long payNo);

  Pay getPaymentByOrder(Long orderNo);
}
