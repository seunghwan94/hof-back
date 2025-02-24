package com.lshwan.hof.domain.entity.member;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  private String id;
  private String email;
  private String reason;
  private LocalDateTime keepUntil;
  private LocalDateTime delDate;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno")
  @JsonIgnore
  private Member member;
}
