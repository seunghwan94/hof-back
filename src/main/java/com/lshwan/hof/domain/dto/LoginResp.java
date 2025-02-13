package com.lshwan.hof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResp {
  private boolean success;
  private String message;
  private String token;
  private int statusCode;

  @Override
  public String toString() {
      return "LoginResp{" +
              "success=" + success +
              ", message='" + message + '\'' +
              ", token='" + token + '\'' +
              ", statusCode=" + statusCode +
              '}';
  }
}
