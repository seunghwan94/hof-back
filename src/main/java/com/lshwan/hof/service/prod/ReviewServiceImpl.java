package com.lshwan.hof.service.prod;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.order.ReviewDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.order.Order;
import com.lshwan.hof.domain.entity.order.OrderItem;
import com.lshwan.hof.domain.entity.order.Review;
import com.lshwan.hof.domain.entity.order.Review.StarRating;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.mapper.OrderItemMapper;
import com.lshwan.hof.repository.order.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService{
private final ReviewRepository reviewRepository;
    private final OrderItemMapper itemMapper;

    /**
     * 1. 리뷰 추가
     */
    @Transactional
    public Review add(Long mno, Long pno, String content,String star) {
        // 1. 구매 여부 확인
        List<OrderItem> results = itemMapper.findFullOrderDetails(mno, pno);

        if (results.isEmpty()) {
          throw new IllegalStateException("구매한 사용자만 리뷰를 남길 수 있습니다.");
        }

        // 첫 번째 OrderItem 사용
        OrderItem orderItem = results.get(0);

        // 연관된 엔티티 추출
        Prod prod = orderItem.getProd();
        Order order = orderItem.getOrder();
        Member member = order.getMember();

        StarRating starRating;
        try {
            starRating = StarRating.valueOf(star.toUpperCase()); // 문자열을 Enum으로 변환
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 별점 값입니다. (허용: ONE, TWO, THREE, FOUR, FIVE)");
        }

        // 2. 리뷰 저장
        Review review = Review.builder()
                .member(member)
                .prod(prod)
                .orderItem(orderItem)
                .content(content)
                .star(starRating) // 기본 별점 설정
                .build();

        return reviewRepository.save(review);
    }

    /**
     * 2. 리뷰 리스트 조회 (DTO 반환)
     */

    public List<ReviewDto> findList(Long pno) {
        List<Review> reviews = reviewRepository.findByProdPno(pno);
        return reviews.stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 3. 리뷰 수정
     */
    @Transactional
    public void modify(Long reviewId, String newContent, StarRating newStar) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. ID: " + reviewId));

        review.setContent(newContent);
        review.setStar(newStar);

        reviewRepository.save(review);
    }

    /**
     * 4. 리뷰 삭제
     */
    @Transactional
    public void remove(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. ID: " + reviewId));

        reviewRepository.delete(review);
    }


}
