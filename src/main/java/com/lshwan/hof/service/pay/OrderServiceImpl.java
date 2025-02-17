package com.lshwan.hof.service.pay;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
  
  @Override
  public boolean verifyOrderAmount(String merchantUid, int paidAmount) {
    // DB에서 주문 정보를 가져와 비교
    int expectedAmount = findOrderPriceByUid(merchantUid);
    return expectedAmount == paidAmount;
  }
  
  @Override
  public void completeOrder(String merchantUid) {
    // 주문을 완료 처리하는 로직
    updateOrderStatus(merchantUid, "결제완료");
  }

  @Override
  public int findOrderPriceByUid(String merchantUid) {
    return 50000;
  }

  @Override
  public void updateOrderStatus(String merchantUid, String status) {
    // DB에서 주문 상태 업데이트
  }
  
}
