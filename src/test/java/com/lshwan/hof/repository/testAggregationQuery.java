package com.lshwan.hof.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.history.mongo.VisitHistory;
import com.lshwan.hof.repository.history.VisitHistoryRepository;

@SpringBootTest
public class testAggregationQuery {
  @Autowired
  private VisitHistoryRepository visitHistoryRepository;
  
  private final Random random = new Random();
  @Test
  public void testAggregationQuery2() {
      List<Map<String, Object>> results = visitHistoryRepository.countVisitsByDate();
      System.out.println("🔍 Aggregation 결과: " + results);
}
 @Test
    public void insertRandomVisitHistory() {
        List<VisitHistory> visitHistories = new ArrayList<>();

        for (int i = 0; i < 7; i++) { // 최근 7일간 데이터 생성
            LocalDate visitDate = LocalDate.now().minusDays(i);

            int visitCount = random.nextInt(11) + 10; // 10~20개 생성

            for (int j = 0; j < visitCount; j++) {
                boolean isMember = random.nextBoolean(); // 회원 여부 랜덤 결정
                boolean isMobile = random.nextBoolean(); // 모바일 여부 랜덤 결정

                VisitHistory visit = VisitHistory.builder()
                        .ipAddress("192.168.1." + random.nextInt(255)) // 랜덤 IP
                        .userAgent(isMobile ? "Mobile" : "PC")
                        .visitedUrl("/api/v1/admin/visit")
                        .visitTime(LocalDateTime.now().minusHours(random.nextInt(24)))
                        .visitDate(visitDate)
                        .memberId(isMember ? (long) (random.nextInt(100) + 1) : null) // 회원 ID or 비회원
                        .build();

                visitHistories.add(visit);
            }
        }

        visitHistoryRepository.saveAll(visitHistories);
        System.out.println(" 방문 기록 저장 완료! 총 " + visitHistories.size() + "개 삽입됨.");
    }
}
