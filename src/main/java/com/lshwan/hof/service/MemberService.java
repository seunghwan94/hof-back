package com.lshwan.hof.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lshwan.hof.domain.entity.member.Member;

public interface MemberService /*extends UserDetailsService*/{
  Long write(Member member); 

  boolean login(String id, String pw);

  Member findBy(String id);

  List<Member> findList();

  // boolean remove(String id);
}
