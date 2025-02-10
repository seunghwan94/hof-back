package com.lshwan.hof.domain.entity.history;

import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_history_order")
@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory extends BaseEntityRegDate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;  // 히스토리 번호 (Primary Key)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;  // 주문 번호 (Foreign Key)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member;  // 회원 번호 (Foreign Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderAction action;  // 주문 상태

    @Column(name = "total_price")
    private Integer totalPrice;  // 주문 총 금액

    @Column(length = 255)
    private String reason;  // 취소/환불 사유
    public enum OrderAction {
      ORDER_CREATED, ORDER_CANCELLED, PAYMENT_COMPLETED, PAYMENT_FAILED, REFUNDED
  }
}
