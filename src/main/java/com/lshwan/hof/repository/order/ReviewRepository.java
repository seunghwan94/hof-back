package com.lshwan.hof.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.order.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
   // 특정 상품의 리뷰 목록 조회
   List<Review> findByProdPno(Long pno);
}
