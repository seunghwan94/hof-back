package com.lshwan.hof.controller.member;

import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.service.login.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
@Log4j2
public class ValidController {
  
    private final MemberService memberService; 

    /**
     * 아이디 중복 체크 API
     * @param id (사용자 아이디)
     * @return 아이디 사용 가능 여부
     */
    @PostMapping("/checkid")
    public ResponseEntity<?> checkId(@RequestBody Map<String, String> request) {
        String id = request.get("id");
        // DB에서 해당 아이디가 존재하는지 확인
        boolean isIdAvailable = memberService.isIdAvailable(id);
        log.info("isIdAvailable1 테스트 확인 : ",isIdAvailable); 
        // 결과 반환
        if (isIdAvailable) {
          return ResponseEntity.ok("사용 가능한 아이디입니다.");
        } else {
          return ResponseEntity.ok("사용 중인 아이디입니다.");
        }
    }

}
