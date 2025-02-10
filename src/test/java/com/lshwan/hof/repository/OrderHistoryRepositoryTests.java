package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.history.OrderHistory;
import com.lshwan.hof.domain.entity.history.OrderHistory.OrderAction;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.order.Order;
import com.lshwan.hof.repository.history.OrderHistoryRepository;

@SpringBootTest
public class OrderHistoryRepositoryTests {
  @Autowired
  private OrderHistoryRepository repository;
  @Test
  public void insertTest(){
    OrderHistory history = OrderHistory.builder()
    .order(Order.builder().no(1L).build())
    .member(Member.builder().mno(5L).build())
    .action(OrderAction.ORDER_CREATED)
    .totalPrice(50000)
    .reason(null)
    .build();
    repository.save(history);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
  
}
