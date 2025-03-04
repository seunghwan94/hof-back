package com.lshwan.hof.controller.admin;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.service.history.VisitHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/visit")
@RequiredArgsConstructor

public class VisitHistoryController {
  private final VisitHistoryService visitHistoryService;

    @GetMapping
    @Operation(summary = "방문자 기록MongoDb Insert", description = "MongDb를 활용해 사이트 도메인에 접속하면 모바일,웹,비로그인,로그인을 판별하여 Insert를합니다")
    public String logVisitor(HttpServletRequest request, HttpServletResponse response, 
                             @RequestParam(name = "member",required = false) Long memberId) {
        boolean isNewVisit = visitHistoryService.saveVisitHistory(request, response, memberId);
        return isNewVisit ? "방문 기록이 저장되었습니다." : "이미 오늘 방문한 기록이 있습니다.";
    }
    @GetMapping("/stats")
    @Operation(summary = "대쉬보드의 방문자 기록 차트화 데이터", description = "대쉬보드의방문자 기록 데이터를 반환합니다")
    public  Map<String, Map<String, Map<String, Integer>>> getVisitStats() {
        return visitHistoryService.getDetailedVisitStatistics();
    }
}
