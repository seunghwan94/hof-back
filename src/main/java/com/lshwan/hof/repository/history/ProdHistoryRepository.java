package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.ProdHistory;

public interface ProdHistoryRepository extends JpaRepository<ProdHistory,Long>{
  
}
