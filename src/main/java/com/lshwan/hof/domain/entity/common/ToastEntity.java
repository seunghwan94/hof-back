package com.lshwan.hof.domain.entity.common;

import com.lshwan.hof.domain.entity.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(name = "tbl_toast")  // 테이블 명 명시
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToastEntity extends BaseEntityRegDate{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;  // Toast no (Primary Key)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member;  // 회원번호 (Foreign Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToastType type;  // 알림 유형

    @Column(nullable = false)
    private String message;  // 알림 내용

    @Builder.Default
    @Column(name = "is_read")
    private boolean isRead = false;  // 읽음 여부 (기본값: false)

    public enum ToastType {
        INFO, WARNING, ALERT, PROMO
    }
}
