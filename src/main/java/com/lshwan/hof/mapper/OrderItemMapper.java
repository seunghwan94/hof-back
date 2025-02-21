package com.lshwan.hof.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.entity.order.OrderItem;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> findFullOrderDetails(@Param("mno") Long mno, @Param("pno") Long pno);
}
