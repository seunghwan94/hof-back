package com.lshwan.hof_backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lshwan.hof_backend.domain.entity.Member;

@Mapper
public interface MemberMapper {
  
  // @Select("SELECT * FROM tbl_wwmember")
  List<Member> findAll();
}
