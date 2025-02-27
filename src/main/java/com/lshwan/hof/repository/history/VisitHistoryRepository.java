package com.lshwan.hof.repository.history;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.lshwan.hof.domain.entity.history.mongo.VisitHistory;


public interface VisitHistoryRepository extends MongoRepository<VisitHistory, String> {

    // 같은 IP가 오늘 방문했는지 확인하는 메서드
    Optional<VisitHistory> findByIpAddressAndVisitDate(String ipAddress, LocalDate visitDate);

    //  회원: IP + memberId + 날짜 기준 조회
    Optional<VisitHistory> findByIpAddressAndMemberIdAndVisitDate(String ipAddress, Long memberId, LocalDate visitDate);


      @Aggregation(pipeline = {
        "{ '$addFields': { " +
            "'isMember': { '$cond': { 'if': { '$or': [ { '$eq': ['$member_id', null] }, { '$not': ['$member_id'] } ] }, 'then': '비회원', 'else': '회원' } }, " +
            "'deviceType': { '$cond': { 'if': { '$regexMatch': { 'input': '$user_agent', 'regex': 'Mobi' } }, 'then': '모바일', 'else': 'PC' } } } " +
        "} }",
        "{ '$group': { " +
            " '_id': { 'visitDate': '$visit_date', 'isMember': '$isMember', 'deviceType': '$deviceType' }, " +
            " 'count': { '$sum': 1 } " +
        "} }",
        "{ '$sort': { '_id.visitDate': 1 } }"
    })
  
  
    List<Map<String, Object>> countVisitsByDate();
  
}