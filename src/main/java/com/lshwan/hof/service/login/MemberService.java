package com.lshwan.hof.service.login;

import java.util.List;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberDetail;

public interface MemberService {

  // Long signUp(Member member); 

  Long write(Member member); 

  boolean isIdAvailable(String id);

  boolean login(String id, String pw);

  Member findBy(String id);

  List<Member> findList();

  // boolean remove(String id);
  MemberDetail verificationBefore(String email);


}
