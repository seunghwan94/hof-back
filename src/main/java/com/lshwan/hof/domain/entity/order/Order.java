package com.lshwan.hof.domain.entity.order;

import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tbl_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_no")
    private Delivery delivery; 

    @Column(nullable = false)
    private int totalPrice;
}