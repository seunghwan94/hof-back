package com.lshwan.hof.service.prod;

import java.util.List;

import com.lshwan.hof.domain.dto.order.ReviewDto;
import com.lshwan.hof.domain.entity.order.Review;
import com.lshwan.hof.domain.entity.order.Review.StarRating;

public interface ReviewService {

  Review add(Long mno, Long pno, String content,String star);
  List<ReviewDto> findList(Long pno);
  void modify(Long reviewId, String newContent, StarRating newStar);
  void remove(Long reviewId);
}
