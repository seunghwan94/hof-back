package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.Member;

public interface MemberDetailRepository extends JpaRepository<Member, Long>{
  
}
