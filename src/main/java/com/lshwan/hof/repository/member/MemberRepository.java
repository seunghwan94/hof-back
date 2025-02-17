package com.lshwan.hof.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
  Optional<Member> findById(String id);
  boolean existsById(String id);
}