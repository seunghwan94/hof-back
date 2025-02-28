package com.lshwan.hof.domain.dto.order;

import com.lshwan.hof.domain.entity.order.Review;
import com.lshwan.hof.domain.entity.order.Review.StarRating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;
    private String content;
    private Long mno;
    private String memberName;
    private String prodTitle;
    private StarRating star;

    // 생성자 - 엔티티를 DTO로 변환
    public ReviewDto(Review review) {
        this.reviewId = review.getNo();
        this.content = review.getContent();
        this.mno = review.getMember().getMno();
        this.memberName = review.getMember().getName();
        this.prodTitle = review.getProd().getTitle();
        this.star = review.getStar();
    }
}
