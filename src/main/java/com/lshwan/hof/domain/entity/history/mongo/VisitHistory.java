
package com.lshwan.hof.domain.entity.history.mongo;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.*;

@Document(collection = "visit_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitHistory {

    @Id
    private String id; // MongoDB의 ObjectId

    @Field("ip_address")
    private String ipAddress; 

    @Field("user_agent")
    private String userAgent;

    @Field("visited_url")
    private String visitedUrl; 

    @Field("visit_time")
    private LocalDateTime visitTime; 

    @Field("member_id")
    private Long memberId; // 로그인한 사용자 ID (비회원이면 null)
}