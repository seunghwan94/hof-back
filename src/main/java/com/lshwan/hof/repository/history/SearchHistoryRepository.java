package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>{
  
}
