package com.lshwan.hof.domain.entity.history;

import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.ProdCategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "tbl_search_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistorySearch extends BaseEntityRegDate{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false, foreignKey = @ForeignKey(name = "fk_search_history_member"))
    private Member member; 

    @Column(name = "keyword", nullable = false)
    private String keyword; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cno", nullable = false, foreignKey = @ForeignKey(name = "fk_search_history_category"))
    private ProdCategory category; 

    @Column(name = "search_session_id", nullable = false)
    private UUID searchSessionId; 

    @Column(name = "is_push_msg", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isPushMsg; 

}
