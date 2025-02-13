package com.lshwan.hof.domain.entity.history;

import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.history.mongo.HistorySearch;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "tbl_pay_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPay extends BaseEntityRegDate{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno", nullable = false)
    private Prod prod; 
    
    @Column(name = "searchHistoryNo", length = 24, nullable = false)
    private String searchHistoryNo; // ✅ MongoDB `_id`(ObjectId)를 String으로 저장

    @Column(name = "search_session_id")
    private UUID searchSessionId; 

}
