package com.lshwan.hof.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.prod.ProdDetailDto;
import com.lshwan.hof.domain.entity.prod.view.ProdView;
import com.lshwan.hof.service.prod.ProdDetailService;
import com.lshwan.hof.service.prod.view.ProdViewService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("prod")
public class ProdController {
  private ProdViewService service;
  private final ProdDetailService prodDetailService;
  
  @GetMapping
  public ResponseEntity<?> index() {
    return ResponseEntity.ok().body(service.findList());
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProdView>> searchProd(@RequestParam(required = false) String title, @RequestParam(required = false) Long cno) {
    List<ProdView> products;
    
    if (title != null) {
      products = service.findByTitle(title);
    } else if (cno != null) {
      products = service.findByCno(cno);
    } else {
      return ResponseEntity.badRequest().body(null);
    }

    return ResponseEntity.ok(products);
  }
  
  @GetMapping("/{pno}")
  public ResponseEntity<ProdDetailDto> getProdDetail(@PathVariable Long pno) {
    ProdDetailDto prodDetail = prodDetailService.findBy(pno);
    
    if (prodDetail == null) {
      return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(prodDetail);
  }
}
