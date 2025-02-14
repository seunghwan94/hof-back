package com.lshwan.hof.mapper.util;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lshwan.hof.domain.dto.SearchRequestDto;

@Mapper
public interface SearchMapper {
  List<Map<String, Object>> search(@Param("request") SearchRequestDto request);
}
