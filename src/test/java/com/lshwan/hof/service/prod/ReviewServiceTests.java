package com.lshwan.hof.service.prod;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.order.ReviewDto;
import com.lshwan.hof.domain.entity.order.Review;
import com.lshwan.hof.domain.entity.order.Review.StarRating;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ReviewServiceTests {
  @Autowired
  private ReviewService service;

  @Test
  public void testAddReview() {
    Review review = service.add(24L, 406L, "정말 좋은 제품입니다!","five");
    assertNotNull(review);
  }

  @Test
  public void testFindReviews() {
    List<ReviewDto> reviews = service.findList(406L);
    assertNotNull(reviews);
  }

  @Test
  public void testModifyReview() {
    service.modify(2L, "수정된 리뷰 내용입니다.", StarRating.FIVE);
  }

  @Test
  public void testRemoveReview() {
    service.remove(2L);
  }

}
