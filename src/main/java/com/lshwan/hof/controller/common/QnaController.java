package com.lshwan.hof.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.QnaDto;
import com.lshwan.hof.domain.entity.common.Qna;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.service.common.QnaService;
import com.lshwan.hof.service.login.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RequestMapping("common/qna")
@RestController
@Log4j2
public class QnaController {
  ///////////////////////qna
  @Autowired
  private QnaService qnaService;
    @Autowired
  private MemberService service;
@GetMapping
@Operation(summary = "❓ 상품문의 리스트 조회 ", description = "등록된 모든 상품문의리스트를 조회합니다")
public ResponseEntity<List<QnaDto>> getQnaList() {
  List<QnaDto> dtoList = qnaService.findList();
  return ResponseEntity.ok(dtoList);
}
@PostMapping
@Operation(summary = "상품 문의 답변 등록", description = "상품문의 답변을 등록합니다")
public ResponseEntity<?> registoryQna(@RequestBody QnaDto dto) {
  Member member = service.findBy(dto.getMemberId()); 
  if(member == null){
    member = Member.builder().id("hof").build();
  }

    Qna parentQna = (dto.getParentNo() != null) ? qnaService.findBy(dto.getParentNo()) : null;


    Long newId = qnaService.add(dto, member, parentQna);

    return ResponseEntity.ok("등록 완료: " + newId);
}
@PutMapping("{qno}")
public ResponseEntity<?> modifyQna(@PathVariable("qno") Long qno, @RequestBody Qna entity) {
    Qna existingQna = qnaService.findBy(qno);

    Long updatedId = qnaService.modify(existingQna);

    return ResponseEntity.ok().body("수정 완료: " + updatedId);
}
@DeleteMapping("{qno}")
@Operation(summary = "상품문의 삭제", description = "관리자가 상품문의 등록된걸 삭제합니다")
public ResponseEntity<?> deleteQna(@PathVariable("qno") Long qno){

  qnaService.remove(qno);
  return ResponseEntity.ok().body("삭제 완료" );
}

@GetMapping("/qna/{pno}")
@Operation(summary = " 특정 상품 문의 조회", description = "상품 상세 페이지에서 특정 상품에 대한 문의만 조회")
public ResponseEntity<List<QnaDto>> getQnaListByProduct(@PathVariable("pno") Long pno) {
    List<QnaDto> dtoList = qnaService.findByProduct(pno);
    return ResponseEntity.ok(dtoList);
}
  
}
