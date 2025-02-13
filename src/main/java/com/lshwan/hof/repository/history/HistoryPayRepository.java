package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.HistoryPay;

public interface HistoryPayRepository extends JpaRepository<HistoryPay,Long>{
  
}
