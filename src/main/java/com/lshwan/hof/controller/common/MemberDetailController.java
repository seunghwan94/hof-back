package com.lshwan.hof.controller.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.member.MemberDetailDto;
import com.lshwan.hof.service.login.MemberDetailService;

import io.swagger.v3.oas.annotations.Operation;
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
      @Operation(summary = "맴버 상세정보 api", description = "맴버 상세정보를 조회합니다")
    public ResponseEntity<MemberDetailDto> getMemberDetail(@PathVariable(name = "mno") Long mno) {
      MemberDetailDto memberDetail = memberDetailService.getMemberDetail(mno);
      return ResponseEntity.ok(memberDetail);
    }

     @PutMapping("/{mno}")
     @Operation(summary = "맴버 업데이트 api", description = "맴버 의 정보를 업데이트 합니다")
    public ResponseEntity<String> updateMember(@PathVariable(name="mno") Long mno, @RequestBody MemberDetailDto updatedDto) {
      memberDetailService.updateMember(mno, updatedDto);
        return ResponseEntity.ok("회원 정보가 성공적으로 업데이트되었습니다.");
    }
}
