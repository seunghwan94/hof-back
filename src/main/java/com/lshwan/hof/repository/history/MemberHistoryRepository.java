package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.MemberHistory;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory,Long> {
  
}
