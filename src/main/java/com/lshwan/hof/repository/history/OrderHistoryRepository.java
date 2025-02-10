package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Long>{
  
}
