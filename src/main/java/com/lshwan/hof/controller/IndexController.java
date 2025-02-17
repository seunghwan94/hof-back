package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.lshwan.hof.domain.dto.PageRequestDto;
import com.lshwan.hof.domain.dto.PageResultDto;
import com.lshwan.hof.domain.dto.ProdDto;
import com.lshwan.hof.domain.dto.SearchRequestDto;
import com.lshwan.hof.domain.entity.admin.FWL;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.service.admin.FwlService;
import com.lshwan.hof.service.login.MemberService;
import com.lshwan.hof.service.prod.ProdService;
import com.lshwan.hof.service.util.SearchService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;






@RestController
@AllArgsConstructor
@RequestMapping("index")
public class IndexController {
  @Autowired
  private MemberService service;
  @Autowired
  private FwlService fwlService;
      @Autowired
    private SearchService searchService;
  @Autowired
  private ProdService prodService;
    
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
  @GetMapping("/fwl")
  public List<FWL> fwlList() {
    return fwlService.findList();
  }
  @PutMapping("/fwl/update/{fno}")
  public ResponseEntity<?> putMethodName(@PathVariable Long fno, @RequestBody FWL entity) {

    FWL existingFwl = fwlService.findBy(fno);


    existingFwl.setIsActive(entity.getIsActive());


    Long updatedId = fwlService.modify(existingFwl);

    return ResponseEntity.ok().body("수정 완료: " + updatedId);
  }
  @DeleteMapping("/fwl/delete")
  public ResponseEntity<?> deleteFwl(@RequestBody List<Long> fnoList) {
  //   if (fnoList == null || fnoList.isEmpty()) {
  //     return ResponseEntity.badRequest().body("삭제할 항목을 선택하세요.");
  // }
      int deleteCount = 0;
      for (Long fno : fnoList) {
          if (fwlService.remove(fno)) {
             System.out.println(" fno ::::::::::::::::::::::::: "+ fno);
              deleteCount++;
          }
      }
      return ResponseEntity.ok().body("삭제 완료: " + deleteCount + "개 항목");
  }
  @PostMapping("/fwl/add")
  public ResponseEntity<?> addFwl(@RequestBody FWL entity) {
    Long newId = fwlService.add(entity);
    return ResponseEntity.ok().body("등록 완료: " + newId);
}
  
/////////////////////////prod
@GetMapping("/prod")
    public ResponseEntity<PageResultDto<ProdDto, Prod>> getProdList(@RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String keyword) {
        PageRequestDto dto = PageRequestDto.builder()
        .page(page)
        .size(size)
        .type(type)
        .keyword(keyword)
        .build();
        return ResponseEntity.ok().body(prodService.findList(dto));
    }
  
}
