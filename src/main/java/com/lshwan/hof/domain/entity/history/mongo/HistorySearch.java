package com.lshwan.hof.domain.entity.history.mongo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "search_history1")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistorySearch{

    @Id
    private String id; // MongoDB에서는 기본적으로 String 타입의 _id 사용
    @Field("mno")
    private Long mno; // 회원 정보 (참조)
    
    @Field("keyword")
    private String keyword; // 검색 키워드

    private Long cno; // 카테고리 (참조)

    @Field("search_session_id")
    private UUID searchSessionId; // 검색 세션 ID

    @Field("is_push_msg")
    private boolean isPushMsg; // 푸시 메시지 여부 (기본값 false)

    @Field("reg_date")
    private LocalDateTime regDate; // 생성일


}
