package com.lshwan.hof.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.order.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
  
}
