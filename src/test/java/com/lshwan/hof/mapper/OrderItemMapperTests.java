package com.lshwan.hof.mapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.order.OrderItem;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class OrderItemMapperTests {
  @Autowired
  private OrderItemMapper mapper;

  @Test
  void test(){
    Long mno = 24L;
    Long pno = 406L;

    List<OrderItem> results = mapper.findFullOrderDetails(mno,pno);

    for (OrderItem orderItem : results) {
        System.out.println("OrderItem ID: " + orderItem.getNo());
        System.out.println("Product Name: " + orderItem.getProd());
        System.out.println("Member Name: " + orderItem.getOrder().getMember().getName());
        System.out.println("Delivery Status: " + orderItem.getOrder().getDelivery().getStatus());
    }
  }
}