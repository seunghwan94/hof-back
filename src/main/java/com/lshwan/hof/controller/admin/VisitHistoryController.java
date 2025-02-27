package com.lshwan.hof.controller.admin;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.service.history.VisitHistoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/visit")
@RequiredArgsConstructor

public class VisitHistoryController {
  private final VisitHistoryService visitHistoryService;

    @GetMapping
    public String logVisitor(HttpServletRequest request, HttpServletResponse response, 
                             @RequestParam(name = "member",required = false) Long memberId) {
        boolean isNewVisit = visitHistoryService.saveVisitHistory(request, response, memberId);
        return isNewVisit ? "방문 기록이 저장되었습니다." : "이미 오늘 방문한 기록이 있습니다.";
    }
    @GetMapping("/stats")
    public  Map<String, Map<String, Map<String, Integer>>> getVisitStats() {
        return visitHistoryService.getDetailedVisitStatistics();
    }
}
