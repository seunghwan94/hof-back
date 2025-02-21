package com.lshwan.hof.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface CartMapper {
    // 회원 번호로 장바구니 항목 삭제
    void deleteByMemberMno(@Param("mno") Long mno);
}
