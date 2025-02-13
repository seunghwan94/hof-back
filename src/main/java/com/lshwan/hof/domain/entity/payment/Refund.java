package com.lshwan.hof.domain.entity.payment;

import com.lshwan.hof.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbl_refund")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_no", nullable = false)
    private Pay pay; 

    @Column
    private String reason; 

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean status = false; 

    @Column(nullable = false)
    private int refundPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundMethod method; 

    @Column(name = "ref_date")
    private LocalDateTime refundDate;

    public enum RefundMethod {
        카드, 이체, 무통장
    }
}
