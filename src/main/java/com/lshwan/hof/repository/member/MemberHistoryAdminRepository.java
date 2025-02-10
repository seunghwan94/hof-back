package com.lshwan.hof.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.MemberHistoryAdmin;

public interface MemberHistoryAdminRepository extends JpaRepository<MemberHistoryAdmin,Long>{
  
}
