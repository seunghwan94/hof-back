package com.lshwan.hof.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
  private String username;
  private String password;
}
