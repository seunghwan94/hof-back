package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.MemberHistory;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory,Long> {
  
}
