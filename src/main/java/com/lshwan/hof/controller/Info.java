package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@Log4j2
public class Info {
  @GetMapping("/")
  public String requestMethodName(@AuthenticationPrincipal UserDetails userDetails, Principal principal) {
    log.info("요청된 사용자 정보: {}", userDetails);
    log.info("요청된 Principal 정보: {}", principal);
    return "success";
  }
}
  


