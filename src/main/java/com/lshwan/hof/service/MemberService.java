package com.lshwan.hof.service;

import com.lshwan.hof.domain.entity.member.Member;

public interface MemberService {
  Long write(Member member); 

  boolean login(String id, String pw);

  Member findBy(String id);
}
