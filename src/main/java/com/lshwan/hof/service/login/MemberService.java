package com.lshwan.hof.service.login;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lshwan.hof.domain.entity.member.Member;

public interface MemberService {

  Long signUp(Member member); 

  Long write(Member member); 

  boolean login(String id, String pw);

  Member findBy(String id);

  List<Member> findList();

  // boolean remove(String id);


}
