package com.lshwan.hof.service.pay;

public interface OrderService {
  boolean verifyOrderAmount(String merchantUid, int paidAmount);
  void completeOrder(String merchantUid);
  int findOrderPriceByUid(String merchantUid);
  void updateOrderStatus(String merchantUid, String status);
}
