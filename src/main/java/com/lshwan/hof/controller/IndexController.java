package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import com.lshwan.hof.domain.entity.Member;
import com.lshwan.hof.service.MemberService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@AllArgsConstructor
@RequestMapping("index")
public class IndexController {
  private MemberService service;

  @GetMapping("/")
  public ResponseEntity<?> index() {
    return ResponseEntity.ok().body(service.write(Member.builder().build()));
  }
  
}
