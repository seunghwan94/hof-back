package com.lshwan.hof.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
  // Optional<Member> findById(String id);
  @Query("SELECT m FROM Member m WHERE m.id = :id")
  Optional<Member> findByLoginId(@Param("id") String id);
  // Optional<Member> findByLoginId(String id);
  boolean existsById(String id);

  @EntityGraph(attributePaths = {"memberAddrList"})
  Optional<Member> findWithAddressesByMno(Long mno);

  Optional<Member> findByMemberDetail_email(String email);
}