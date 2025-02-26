package com.lshwan.hof.service.login;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.member.MemberDetailDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.member.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberDetailServiceImpl implements MemberDetailService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDetailDto getMemberDetail(Long mno) {
      Member member = memberRepository.findWithAddressesByMno(mno)
      .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

      return MemberDetailDto.fromEntity(member);
    }
}