package com.lshwan.hof_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import com.lshwan.hof_backend.domain.entity.Member;
import com.lshwan.hof_backend.service.MemberService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class IndexController {
  private MemberService service;

  @GetMapping("")
  public ResponseEntity<?> index() {
    return ResponseEntity.ok().body(service.write(Member.builder().build()));
  }
  
}
