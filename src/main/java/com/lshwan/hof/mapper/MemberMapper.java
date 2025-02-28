package com.lshwan.hof.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lshwan.hof.domain.dto.member.AdminMemberDto;
import com.lshwan.hof.domain.entity.member.Member;

@Mapper
public interface MemberMapper {
  List<Member> findAll(); 

  Member selectOne(String id);

  List<AdminMemberDto> findAllMembers();
}
