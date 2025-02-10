package com.lshwan.hof.domain.entity.history;

import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.member.Member;

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

@Entity
@Table(name = "tbl_history_login")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberHistoryLogin extends BaseEntityRegDate{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;  // 히스토리 번호 (Primary Key)

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno", nullable = false)
  private Member member;  // 회원 번호 (Foreign Key)
  @Column(nullable = false)
  private boolean success;  // 로그인 성공 여부
  @Column(name = "ip_address")
  private String ipAddress;  // 접속 IP
  @Column(name = "user_agent")
  private String userAgent;  // 브라우저/OS 정보
  @Column(name = "fail_reason")
  private String failReason;  // 로그인 실패 사유


}
