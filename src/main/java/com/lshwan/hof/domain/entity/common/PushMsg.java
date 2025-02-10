package com.lshwan.hof.domain.entity.common;



import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.ProdCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Table(name = "tbl_push_msg")  // 테이블 명 명시
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushMsg extends BaseEntityRegDate{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;  // 푸쉬 알림 번호 (Primary Key)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member;  // 회원 번호 (Foreign Key)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cno", nullable = false)
    private ProdCategory category;  // 카테고리 번호 (Foreign Key)
    
    @Default
    @Column(nullable = false)
    private int count = 0;  // 메세지 알림 카운트 (기본값: 0)
}
