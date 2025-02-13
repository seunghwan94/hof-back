package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.HistoryReview;

public interface HistoryReviewRepository extends JpaRepository<HistoryReview, Long>{
  
}
