package com.lshwan.hof.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.MemberAddr;

public interface MemberAddrRepository extends JpaRepository<MemberAddr, Long>{
  
}
