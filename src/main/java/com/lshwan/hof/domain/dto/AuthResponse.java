package com.lshwan.hof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
  private String accessToken;  // JWT 토큰
  private String tokenType;    // Bearer (고정값)
  private String username;     // 사용자명
}

