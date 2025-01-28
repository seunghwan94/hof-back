package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import com.lshwan.hof.domain.entity.Member;
import com.lshwan.hof.service.MemberService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
public class IndexController {
  private MemberService service;

  @GetMapping("")
  public ResponseEntity<?> index() {
    return ResponseEntity.ok().body(service.write(Member.builder().build()));
  }
  
}
