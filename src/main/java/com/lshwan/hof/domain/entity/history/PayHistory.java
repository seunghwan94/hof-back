package com.lshwan.hof.domain.entity.history;

import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;
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
public class PayHistory extends BaseEntityRegDate{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false, foreignKey = @ForeignKey(name = "fk_pay_history_member"))
    private Member member; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno", nullable = false, foreignKey = @ForeignKey(name = "fk_pay_history_prod"))
    private Prod prod; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_history_no", nullable = false, foreignKey = @ForeignKey(name = "fk_pay_history_search_history"))
    private SearchHistory searchHistory; 

    @Column(name = "search_session_id")
    private UUID searchSessionId; 

}
