package com.lshwan.hof.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.config.JwtTokenProvider;
import com.lshwan.hof.domain.dto.LoginReq;
import com.lshwan.hof.domain.dto.LoginResp;
import com.lshwan.hof.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
      this.authenticationManager = authenticationManager;
      this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
      try {
          Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);

          // 로그인 성공 시 JWT 토큰 생성
          String token = jwtTokenProvider.createToken(loginReq.getUsername());

          LoginResp response = LoginResp.builder()
          .success(true)
          .message("로그인 성공")
          .token(token)
          .statusCode(200)
          .build();
          return ResponseEntity.ok(response);
          // return ResponseEntity.ok(new LoginResp(true, "로그인 성공", token));
      } catch (Exception e) {
        // 로그인 실패 응답
        LoginResp response = LoginResp.builder()
          .success(false)
          .message("id나 pw가 틀렸습니다")
          .statusCode(401)
          .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResp(false, "id나 pw가 틀렸습니다"));
    }
  }
}
