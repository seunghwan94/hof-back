package com.lshwan.hof.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lshwan.hof.config.JwtTokenProvider;
import com.lshwan.hof.domain.dto.auth.AuthRequestDto;
import com.lshwan.hof.domain.dto.auth.AuthResponseDto;
import com.lshwan.hof.domain.dto.member.MemberDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberDetail;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.service.login.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final MemberService memberService; 
  private final MemberRepository memberRepository;
  

  /**
   * 로그인 API: 사용자 인증 후 JWT 토큰 발급
   * @param authRequest (사용자 ID, 비밀번호)
   * @return JWT 포함된 JSON 응답
   */
  @PostMapping("/login")
  @Operation(summary = "토큰 기반 로그인 JWT 인증방식 ", description = "토큰 기반 로그인 로직")
  public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequest) {
    log.info("로그인 요청 받음!!! username: {}", authRequest.getUsername());
    log.info(authRequest);
    try {
      log.info("[AuthController] AuthenticationManager 실행 전: {}", authenticationManager);
      // 1️ 사용자를 인증 (Spring Security에서 자동으로 유효성 검사)
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
      log.info("어던티케이션 인증 성공: {}", authentication.getPrincipal());
      log.info("[AuthController] AuthenticationManager 실행 완료, 결과: {}", authentication);
      
      // 인증 성공 시 UserDetails 가져오기
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      log.info("인증 성공: {}", userDetails.getUsername());

      // 2 db에서 사용자 정보 조회
      Member member = memberRepository.findByLoginId(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

      // 3️ JWT 토큰 생성
      // String token = jwtTokenProvider.generateToken(userDetails.getUsername());
      String token = jwtTokenProvider.generateToken(member.getId());
      log.info("JWT 토큰 생성 완료!!!");      

      // dto변환 pw제외
      MemberDto memberDto = MemberDto.builder()
        .mno(member.getMno())
        .id(member.getId())
        .name(member.getName())
        .role(member.getRole().name())
        .regDate(member.getRegDate().toString())
        .modDate(member.getModDate().toString())
        .build();

      // 4️ 응답 객체 생성
      // AuthResponseDto authResponse = new AuthResponseDto(token, "Bearer", memberDto);
      AuthResponseDto authResponse = AuthResponseDto.builder()
        .accessToken(token)
        .tokenType("Bearer")
        .member(memberDto)
        .build();
      log.info("AuthResponse 생성 완료: {}", authResponse);
        
        // 5️ JSON 형태로 응답 반환
      return ResponseEntity.ok(authResponse);

    } catch (Exception e) {
      log.error("로그인 실패: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호 확인 필요!!!");
    }
  }


  /**
   * 회원가입 API: 사용자 정보를 저장
   * @param member (id, pw, name, role)
   * @return 생성된 회원의 mno 반환
   */

  @PostMapping("/signup")
  @Operation(summary = "회원가입 ", description = "회원가입 에 필요한 파라미터와 성공여부를 나타냅니다")
  public ResponseEntity<?> signup(@RequestBody Map<String, Object> request) {
    
    // Member 객체 생성
    ObjectMapper objectMapper = new ObjectMapper();
    Member member = objectMapper.convertValue(request, Member.class);

    if (request.containsKey("memberDetail")) {
        MemberDetail memberDetail = objectMapper.convertValue(request.get("memberDetail"), MemberDetail.class);
        member.setMemberDetailJson(memberDetail);
    } else {
        log.error("회원가입 실패: MemberDetail이 null입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: 회원 상세 정보를 확인하세요.");

    } 
    try {
      Long mno = memberService.write(member);
      log.info("log.info 회원가입 성공 mno: {}", mno);
      return ResponseEntity.ok("회원가입이 완료되었습니다.");
    } catch(Exception e) {
          log.error("log.info 회원가입 실패: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: 다시 시도하세요.");
    }
  }
}