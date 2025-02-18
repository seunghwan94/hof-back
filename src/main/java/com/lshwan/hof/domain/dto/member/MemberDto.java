package com.lshwan.hof.domain.dto.member;

import com.lshwan.hof.domain.entity.member.Member.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
  private Long mno;
  private String id;
  private String name;
  private String role;
  private String regDate;
  private String modDate;
}
