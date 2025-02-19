package com.lshwan.hof.controller.main;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@AllArgsConstructor
@RequestMapping("main/prod")
public class ProdController {
  private final ProdViewService service;
  private final ProdDetailService prodDetailService;

  @GetMapping
  public ResponseEntity<List<ProdView>> searchProd(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "cno",required = false) Long cno) {
    List<ProdView> products; 
    
    if (title != null) {
      products = service.findByTitle(title);
    } else if (cno != null) {
      products = service.findByCno(cno);
    } else {
      products = service.findList();
    }

    return ResponseEntity.ok(products);
  }
  
  @GetMapping("/{pno}")
  public ResponseEntity<ProdDetailDto> getProdDetail(@PathVariable("pno") Long pno) {
    ProdDetailDto prodDetail = prodDetailService.findBy(pno);
    
    if (prodDetail == null) {
      return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(prodDetail);
  }
  @PutMapping("/{pno}")
    public ResponseEntity<?> updateProduct(@PathVariable("pno") Long pno,@RequestBody ProdDetailDto productDto) {

        try {
            productDto.setPno(pno); // DTO에 상품번호 설정
            Long updatedPno = prodDetailService.modify(productDto);
            return ResponseEntity.ok("상품 수정 완료 (Pno: " + updatedPno + ")");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("상품 수정 실패: " + e.getMessage());
        }
    }
    @DeleteMapping("/{ono}")
    public ResponseEntity<?> deleteOption(@PathVariable("ono") Long optionNo) {
      boolean isDeleted = prodDetailService.removeOption(optionNo);
      if (isDeleted) {
          return ResponseEntity.ok().body("옵션이 삭제되었습니다.");
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 옵션이 존재하지 않습니다.");
    }
    @DeleteMapping("/prod/{pno}")
    public ResponseEntity<?> deleteprod(@PathVariable("pno") Long pno) {
      boolean isDeleted = prodDetailService.remove(pno);
      if (isDeleted) {
          return ResponseEntity.ok().body("상품이 삭제되었습니다.");
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품이 존재하지 않습니다.");
    }
}
