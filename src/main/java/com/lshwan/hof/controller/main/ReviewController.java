package com.lshwan.hof.controller.main;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.order.ReviewDto;
import com.lshwan.hof.domain.dto.order.ReviewRequest;
import com.lshwan.hof.domain.entity.order.Review;
import com.lshwan.hof.domain.entity.order.Review.StarRating;
import com.lshwan.hof.service.prod.ReviewService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("main/reviews")
@AllArgsConstructor
@Log4j2
public class ReviewController {
  
  private final ReviewService reviewService;

  /**
   * 1. 리뷰 등록
   * @param mno - 회원 번호
   * @param pno - 상품 번호
   * @param content - 리뷰 내용
   */
  @PostMapping("/add")
  public ResponseEntity<?> addReview(@RequestBody ReviewRequest reviewRequest) {
    Long mno = reviewRequest.getMno();
    Long pno = reviewRequest.getPno();
    String content = reviewRequest.getContent();
    String star = reviewRequest.getStar();
    try {
      Review savedReview = reviewService.add(mno, pno, content, star);
      return ResponseEntity.ok(savedReview);
    } catch (IllegalStateException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 등록 중 오류가 발생했습니다.");
    }
  }

  /**
   * 2. 특정 상품 리뷰 목록 조회
   * @param pno - 상품 번호
   * @return List<Review>
   */
  @GetMapping("/list/{pno}")
  public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable Long pno) {
    List<ReviewDto> reviews = reviewService.findList(pno);
    return ResponseEntity.ok(reviews);
  }

  /**
   * 3. 리뷰 수정
   * @param reviewId - 리뷰 ID
   * @param newContent - 수정할 리뷰 내용
   * @param newStar - 수정할 별점
   */
  @PutMapping("/modify/{reviewId}")
  public ResponseEntity<String> modifyReview(@PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest) {
    String newContent = reviewRequest.getContent();
    String newStar = reviewRequest.getStar();

    try {
      reviewService.modify(reviewId, newContent, StarRating.valueOf(newStar.toUpperCase()));
      return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 수정 중 오류가 발생했습니다.");
    }
  }

  /**
   * 4. 리뷰 삭제
   * @param reviewId - 리뷰 ID
   */
  @DeleteMapping("/remove/{reviewId}")
  public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
    try {
      reviewService.remove(reviewId);
      return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 삭제 중 오류가 발생했습니다.");
    }
  }
}