package com.lshwan.hof.domain.entity.member;


import java.util.ArrayList;
import java.util.List;

import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.note.Note;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Table(name = "tbl_member")  // 테이블 명 명시
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;
  
  private String id;
  private String pw;
  private String name;
  
  @Enumerated(EnumType.STRING)  // ENUM 타입 매핑
  private MemberRole role; 

  @Default
  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.REMOVE)
  private List<Note> notes = new ArrayList<>();

}
