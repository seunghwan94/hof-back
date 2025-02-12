package com.lshwan.hof.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.LoginReq;
import com.lshwan.hof.domain.dto.LoginResp;
import com.lshwan.hof.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController {
  private AuthenticationManager authenticationManager;
  private MemberService memberService;

  public AuthController(AuthenticationManager authenticationManager, MemberService memberService) {
    this.authenticationManager = authenticationManager;
    this.memberService = memberService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginReq LoginReq) {
      try {
        Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(LoginReq.getUsername(), LoginReq.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new LoginResp(true, "로그인 성공"));
      }
      catch(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResp(false, "id나 pw가 틀렸습니다"));

      }
  }
  

}
