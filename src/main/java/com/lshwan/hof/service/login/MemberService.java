package com.lshwan.hof.service.login;

import java.util.List;

import com.lshwan.hof.domain.dto.member.AdminMemberDto;
import com.lshwan.hof.domain.dto.member.MemberDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberDetail;

public interface MemberService {

  Long write(Member member);
  // Long write(Member member, MemberDetail memberDetail);

  boolean isIdAvailable(String id);

  boolean login(String id, String pw);

  Member findBy(String id);

  List<Member> findList();

  MemberDetail verificationBefore(String email);

  Long update(Member member);
  // boolean verifyEmail(String email, String verificationCode);
  // boolean remove(String id);
   List<AdminMemberDto> adminMemberList();

}
