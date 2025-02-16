package com.lshwan.hof.domain.entity.order;

import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Getter
@Table(name = "tbl_review")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno", nullable = false)
    private Prod prod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_no", nullable = false)
    private OrderItem orderItem;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.ORDINAL)
    @Default
    private StarRating star = StarRating.FIVE; 

    public enum StarRating {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
        
        private final int value;

        StarRating(int value) {
            this.value = value;
        }
        
        public int getValue() { 
            return value;
        }
    }
}
