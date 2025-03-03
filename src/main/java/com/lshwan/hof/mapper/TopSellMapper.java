package com.lshwan.hof.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopSellMapper {
    List<Map<String, Object>> getTopSellingProducts();
}
