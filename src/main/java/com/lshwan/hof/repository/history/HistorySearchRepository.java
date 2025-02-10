package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.HistorySearch;

public interface HistorySearchRepository extends JpaRepository<HistorySearch, Long>{
  
}
