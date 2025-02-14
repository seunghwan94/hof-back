package com.lshwan.hof.controller;

import com.lshwan.hof.config.JwtTokenProvider;
import com.lshwan.hof.domain.dto.AuthRequest;
import com.lshwan.hof.domain.dto.AuthResponse;
import com.lshwan.hof.domain.entity.member.Member;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;


  @PostConstruct
  public void init() {
    log.info("AuthController 초기화 완료!!!");
  }

  /**
   * 로그인 API: 사용자 인증 후 JWT 토큰 발급
   * @param authRequest (사용자 ID, 비밀번호)
   * @return JWT 포함된 JSON 응답
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    log.info("로그인 요청 받음!!! username: {}", authRequest.getUsername());
    log.info(authRequest);
    try {
      // 1️ 사용자를 인증 (Spring Security에서 자동으로 유효성 검사)
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

      // 2️ 인증 성공 시 UserDetails 가져오기
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      log.info("인증 성공: {}", userDetails.getUsername());

      // 3️ JWT 토큰 생성
      // String token = jwtTokenProvider.generateToken(userDetails.getUsername());
      String token = jwtTokenProvider.generateToken(userDetails.getUsername(), ((Member) userDetails).getRole());
      log.info("JWT 토큰 생성 완료!!!");
      // 4️ 응답 객체 생성
      AuthResponse authResponse = new AuthResponse(token, "Bearer", userDetails.getUsername());

      // 5️ JSON 형태로 응답 반환
      return ResponseEntity.ok(authResponse);

    } catch (Exception e) {
      log.error("로그인 실패: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호 확인 필요!!!");
    }
  }

}