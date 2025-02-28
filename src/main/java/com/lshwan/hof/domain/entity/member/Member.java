package com.lshwan.hof.domain.entity.member;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.domain.entity.social.Social;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "tbl_member")  // 테이블 명 명시
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Long mno;
  
  @Column(unique = true, nullable = false)
  private String id;

  @Column(nullable = false)
  private String pw;
  
  @Column(nullable = false)
  private String name;
  
  @Enumerated(EnumType.STRING)  // ENUM 타입 매핑
  @Builder.Default // 4가지의 role중 user를 기본값으로 설정함
  private MemberRole role = MemberRole.user; 

  public enum MemberRole {
    user, company, admin, master
  }

  // @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private MemberDetail memberDetail;
  
  @JsonIgnore
  @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
  private MemberDeleted memberDeleted;

  @JsonIgnore
  @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
  private Remember remember;

  @JsonIgnore
  @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
  private Company company;
  
  @Default
  @JsonIgnore
  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MemberAddr> memberAddrList = new ArrayList<>();
  
  @Default
  @JsonIgnore
  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Social> socialList = new ArrayList<>();

  @Default
  @JsonIgnore
  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.REMOVE)
  private List<Note> notes = new ArrayList<>();

}
