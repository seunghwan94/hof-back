package com.lshwan.hof.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.MemberDeleted;

public interface MemberDeletedRepository extends JpaRepository<MemberDeleted, Long>{
  
}
