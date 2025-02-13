package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.service.MemberService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@AllArgsConstructor
@RequestMapping("index")
public class IndexController {
  @Autowired
  private MemberService service;

  @GetMapping("/")
  public ResponseEntity<?> index() {
    return ResponseEntity.ok().body(service.write(Member.builder().build()));
  }
  @GetMapping("/list")
  public List<Member> listtest() {
      return service.findList();
  }
  
}
