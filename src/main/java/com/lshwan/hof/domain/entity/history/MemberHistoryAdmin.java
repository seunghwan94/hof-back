package com.lshwan.hof.domain.entity.history;

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
@Table(name = "tbl_history_admin")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberHistoryAdmin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;
  

    @ManyToOne(fetch = FetchType.LAZY)  // 관리자 회원 정보와 조인
    @JoinColumn(name = "admin_mno", nullable = false)
    private Member adminMno; // 관리자 회원 번호 (Foreign Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminAction action; // 관리자 작업 유형 (Enum)

    @Column(name = "target_no")
    private Long targetNo; // 작업 대상 (상품번호, 회원번호 등)

    @Column(length = 255)
    private String description; // 작업 설명

    @Column(name = "ip_address", length = 45)
    private String ipAddress; // 관리자 접속 IP
    
    public enum AdminAction {
      PRODUCT_ADDED, PRODUCT_UPDATED, SYSTEM_SETTING_CHANGED
    }
}
