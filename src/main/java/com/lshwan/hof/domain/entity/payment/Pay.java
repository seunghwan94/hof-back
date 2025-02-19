package com.lshwan.hof.domain.entity.payment;

import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder.Default;

@Entity
@Getter
@Table(name = "tbl_pay")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10) default '실패'")
    private PaymentStatus status = PaymentStatus.실패;

    @Column(nullable = false)
    private int totalPrice;

    public enum PaymentMethod {
        카드, 이체, 무통장
    }

    public enum PaymentStatus {
        완료, 실패
    }
}
