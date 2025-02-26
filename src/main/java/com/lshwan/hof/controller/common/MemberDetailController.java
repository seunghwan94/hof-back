package com.lshwan.hof.controller.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.member.MemberDetailDto;
import com.lshwan.hof.service.login.MemberDetailService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("common/member")
@Log4j2
@AllArgsConstructor
public class MemberDetailController {
   private final MemberDetailService memberDetailService;

    // 회원 상세 정보 조회 API (GET)
    @GetMapping("/{mno}")
    public ResponseEntity<MemberDetailDto> getMemberDetail(@PathVariable(name = "mno") Long mno) {
      MemberDetailDto memberDetail = memberDetailService.getMemberDetail(mno);
      return ResponseEntity.ok(memberDetail);
    }
}
