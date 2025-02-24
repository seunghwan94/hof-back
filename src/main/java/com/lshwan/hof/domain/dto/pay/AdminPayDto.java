package com.lshwan.hof.domain.dto.pay;

import com.lshwan.hof.domain.entity.payment.Pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminPayDto {
  private Long no;
  private Long orderNo;
  private String method;
  private String status;
  private int totalPrice;
  private String impUid;//결제 승인아이디
  private String memberName;
  private String memberId;
  private String productTitle;
  public AdminPayDto(Pay pay) {
      this.no = pay.getNo();
      this.orderNo = pay.getOrder().getNo();
      this.method = pay.getMethod().name();
      this.status = pay.getStatus().name();
      this.totalPrice = pay.getTotalPrice();
      this.impUid = pay.getImpUid();
      this.memberName = pay.getOrder().getMember().getName();
      this.memberId = pay.getOrder().getMember().getId();
  }
}