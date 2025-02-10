package com.lshwan.hof.domain.entity.order;

import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tbl_order_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno", nullable = false)
    private Prod prod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_option_no")
    private ProdOption prodOption;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int count = 1;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int basePrice = 0;

    @Column(name = "subtotal_price")
    private Integer subtotalPrice;
}
