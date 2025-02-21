package com.lshwan.hof.domain.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class SignupRequestDto {
  private String id;
  private String pw;

  @JsonIgnore
  private String confirmPw;

  private String name;
  private String email;
  
}
