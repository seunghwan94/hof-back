package com.lshwan.hof.domain.entity.member;

import java.time.LocalDateTime;

import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_history_member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberHistory extends BaseEntityRegDate{
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; // 히스토리 번호 (Primary Key)

    @ManyToOne(fetch = FetchType.LAZY)  // 회원 정보와 조인
    @JoinColumn(name = "mno", nullable = false)
    private Member member; // 회원 번호 (Foreign Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberAction action; // 회원 활동 유형 (Enum)

    @Column(length = 255)
    private String description; // 활동 상세 내용

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent; // 브라우저/OS 정보

    public enum MemberAction {
      LOGIN, LOGOUT, ALERT, PURCHASE,REVIEW,QNA,CANCEL
    }

}
