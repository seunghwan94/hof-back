package com.lshwan.hof_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof_backend.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
  
}