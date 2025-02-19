package com.lshwan.hof.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.MemberDetail;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long>{
  Optional<MemberDetail> findByEmail(String email);
}
