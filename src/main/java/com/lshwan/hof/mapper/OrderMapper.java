package com.lshwan.hof.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.dto.order.OrderHistoryDto;

@Mapper
public interface OrderMapper {
    List<OrderHistoryDto> findOrderHistoryByMember(@Param("memberNo") Long memberNo);
}

