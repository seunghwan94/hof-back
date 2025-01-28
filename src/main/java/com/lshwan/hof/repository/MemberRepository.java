package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
  
}