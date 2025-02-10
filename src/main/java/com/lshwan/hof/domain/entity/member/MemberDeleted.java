package com.lshwan.hof.domain.entity.member;

import java.time.LocalDateTime;

import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_member_deleted")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberDeleted {
  
  @Id
  private Long mno;
  private String id;
  private String email;
  private String reason;
  private LocalDateTime keepUntil;
  private LocalDateTime delDate;

  @OneToOne
  @JoinColumn(name = "mno", nullable = false)
  private Member member;
}
