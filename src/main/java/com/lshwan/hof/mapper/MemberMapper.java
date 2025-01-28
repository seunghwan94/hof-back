package com.lshwan.hof.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lshwan.hof.domain.entity.Member;

@Mapper
public interface MemberMapper {
  List<Member> findAll();

  // @Select("select * from tbl_todo where id = #{id}")
  // Todo selectByOne(int id);

}
