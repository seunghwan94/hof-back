package com.lshwan.hof.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategorySalesMapper {
  List<Map<String, Object>> getCategorySales();
}
