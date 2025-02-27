package com.lshwan.hof.service.history;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.history.mongo.VisitHistory;
import com.lshwan.hof.repository.history.VisitHistoryRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitHistoryService {
    private final VisitHistoryRepository visitHistoryRepository;

    public boolean saveVisitHistory(HttpServletRequest request, HttpServletResponse response, Long memberId) {
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String visitedUrl = request.getRequestURI();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Optional<VisitHistory> existingVisit;
        if (memberId != null) {
            existingVisit = visitHistoryRepository.findByIpAddressAndMemberIdAndVisitDate(ipAddress, memberId, today);
        } else {
            existingVisit = visitHistoryRepository.findByIpAddressAndVisitDate(ipAddress, today);
        }

        if (existingVisit.isPresent()) {
            return false;
        }

        VisitHistory visit = VisitHistory.builder()
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .visitedUrl(visitedUrl)
                .visitTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .visitDate(today)
                .memberId(memberId)
                .build();

        visitHistoryRepository.save(visit);

        String cookieName = (memberId != null) ? "visited_" + memberId : "visited";
        Cookie visitCookie = new Cookie(cookieName, "true");
        visitCookie.setMaxAge(24 * 60 * 60);
        visitCookie.setPath("/");
        response.addCookie(visitCookie);

        return true;
    }

    public Map<String, Map<String, Map<String, Integer>>> getDetailedVisitStatistics() {
      List<Map<String, Object>> rawData = visitHistoryRepository.countVisitsByDate();
      Map<String, Map<String, Map<String, Integer>>> visitData = new TreeMap<>();
  
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  
      for (Map<String, Object> entry : rawData) {
          Map<?, ?> idMap = (Map<?, ?>) entry.get("_id");
          Object dateObj = idMap.get("visitDate");
          String isMember = (String) idMap.get("isMember");
          String deviceType = (String) idMap.get("deviceType");
          int count = ((Number) entry.get("count")).intValue();
  
          if (dateObj == null || isMember == null || deviceType == null) continue;
  
          String dateStr = dateObj.toString().substring(0, 10);
  
          visitData.putIfAbsent(dateStr, new TreeMap<>());
          visitData.get(dateStr).putIfAbsent(isMember, new TreeMap<>());
          visitData.get(dateStr).get(isMember).put(deviceType, count);
      }
  
      return visitData;
  }
  
}
