package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.lshwan.hof.domain.dto.SearchRequestDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.service.MemberService;
import com.lshwan.hof.service.util.SearchService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@AllArgsConstructor
@RequestMapping("index")
public class IndexController {
  @Autowired
  private MemberService service;

      @Autowired
    private SearchService searchService;
    
  @GetMapping("/")
  public ResponseEntity<?> index() {
    return ResponseEntity.ok().body(service.write(Member.builder().build()));
  }
  @GetMapping("/list")
  public List<Member> listtest() {
      return service.findList();
  }
  @PostMapping("/search")
    public List<Map<String, Object>> search(@RequestBody SearchRequestDto request) {
        return searchService.search(request);
    }
}
