package com.lshwan.hof.domain.entity.note;
import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.member.Member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tbl_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nno", nullable = false)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_no")
    private Reply parentReply; 

    @Column(nullable = false, columnDefinition = "int default 0")
    private int depth = 0; 

    @Column(name = "order")
    private Integer order; 

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content; 
}
