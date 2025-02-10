package com.lshwan.hof.domain.entity.member;

import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_member_deleted")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDeleted extends BaseEntity{
  
  @Id
  @OneToOne
  private Long mno;
  private String id;
  private String email;
  private String reason;

}
