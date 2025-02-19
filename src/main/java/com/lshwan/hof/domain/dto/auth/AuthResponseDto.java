package com.lshwan.hof.domain.dto.auth;

import com.lshwan.hof.domain.dto.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {
  private String accessToken;  // JWT 토큰
  private String tokenType;    // Bearer (고정값)
  private MemberDto member;     // 회원정보(pw제외)


}

