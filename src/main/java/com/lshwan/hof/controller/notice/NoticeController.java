package com.lshwan.hof.controller.notice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.note.NoticeDto;
import com.lshwan.hof.service.note.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("admin/notice")
@AllArgsConstructor
public class NoticeController {
  private final NoticeService noticeService;


    @PostMapping
      @Operation(summary = "팝업등록API", description = "팝업 을 등록합니다")
    public ResponseEntity<Long> createNotice(@RequestBody NoticeDto noticeDto) {
        Long createdId = noticeService.add(noticeDto);
        return ResponseEntity.ok(createdId);
    }


    @GetMapping
    @Operation(summary = "팝업 리스트 조회 API", description = "팝업의 리스트를 조회합니다")
    public ResponseEntity<List<NoticeDto>> getNotices() {
        return ResponseEntity.ok(noticeService.findList());
    }


    @GetMapping("/{nno}")
    @Operation(summary = "팝업 상세정보 조회 API", description = "팝업의 상세정보를 조회합니다")
    public ResponseEntity<NoticeDto> getNotice(@PathVariable("nno") Long nno) {
        return ResponseEntity.ok(noticeService.findBy(nno));
    }


    @PutMapping("/{nno}")
    @Operation(summary = "팝업 업데이트 API", description = "팝업을 수정시키는 API입니다")
    public ResponseEntity<Long> updateNotice(@PathVariable("nno") Long nno, @RequestBody NoticeDto noticeDto) {
        noticeDto.setNo(nno);
        Long updatedId = noticeService.modify(noticeDto);
        return ResponseEntity.ok(updatedId);
    }


    @DeleteMapping("/{nno}")
    @Operation(summary = "팝업 삭제  API", description = "팝업의 정보를 삭제합니다")
    public ResponseEntity<?> deleteNotice(@PathVariable("nno") String nno) {
 // "1,2,3" → [1, 2, 3] 변환
      List<Long> nnoList = Arrays.stream(nno.split(","))
                                .map(Long::parseLong)
                                .collect(Collectors.toList());

      if (nnoList.isEmpty()) {
          return ResponseEntity.badRequest().body("삭제할 항목을 선택하세요.");
      }

      int deleteCount = 0;
      for (Long nno1 : nnoList) {
          if (noticeService.remove(nno1)) {
              System.out.println("삭제된 nno: " + nno1);
              deleteCount++;
          }
      }
      return ResponseEntity.ok().body("삭제 완료: " + deleteCount + "개 항목");
    }
    @GetMapping("/random")
    @Operation(summary = "메인 팝업 노출 API", description = "메인에서 등록된 팝업을 랜덤으로 데이터를 보냅니다")
    public ResponseEntity<NoticeDto> getRandomNotice() {
    NoticeDto randomNotice = noticeService.findRandom();
    return ResponseEntity.ok(randomNotice);
    }

}
