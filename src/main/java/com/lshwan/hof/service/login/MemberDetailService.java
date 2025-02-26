package com.lshwan.hof.service.login;

import com.lshwan.hof.domain.dto.member.MemberDetailDto;

public interface MemberDetailService {
  MemberDetailDto getMemberDetail(Long mno); // 회원 상세 정보 조회
}