package com.lshwan.hof.service.pay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.order.OrderDto;
import com.lshwan.hof.domain.dto.order.OrderHistoryDto;
import com.lshwan.hof.domain.dto.order.OrderItemDto;
import com.lshwan.hof.domain.entity.order.Delivery.DeliveryStatus;
import com.lshwan.hof.domain.entity.order.Order;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class OrderServiceTests {
  @Autowired
  private OrderService service; // 실제 Bean 주입

  @Test
  public void createOrderTest() {
    // given
    OrderItemDto orderItemDto = OrderItemDto.builder()
                  .pno(408L)  // 상품 번호
                  .count(2)  // 2개 구매
                  .basePrice(10000)  // 단가 10,000원
                  .subtotalPrice(20000)  // 총 가격 20,000원
                .build();

    OrderDto orderDto = OrderDto.builder()
              .mno(24L)
              .items(List.of(orderItemDto))
            .build();

    // When (주문 생성)
    Order savedOrder = service.createOrder(orderDto);

    // Then (검증)
    assertNotNull(savedOrder);
    assertTrue(savedOrder.getNo() > 0);
  }

  @Test
  public void addDeliveryTest() {
    // given
    OrderItemDto orderItemDto = OrderItemDto.builder()
                  .pno(408L)  // 상품 번호
                  .count(2)  // 2개 구매
                  .basePrice(10000)  // 단가 10,000원
                  .subtotalPrice(20000)  // 총 가격 20,000원
                .build();
    OrderDto orderDto = OrderDto.builder()
                  .mno(24L)
                  .items(List.of(orderItemDto))
                .build();
    Order order = service.createOrder(orderDto);
    // when
    Order updatedOrder = service.addDelivery(order.getNo());
    // then
    assertNotNull(updatedOrder.getDelivery());
    assertEquals(DeliveryStatus.상품준비중, updatedOrder.getDelivery().getStatus());
  }

  @Test
  public void getOrderByIdTest() {
    // target
    Long orderNo = 120L;
    // When
    Order orders = service.getOrderById(orderNo);
    // then
    assertNotNull(orders);
  }

  @Test
  public void getOrdersByMemberTest() {
    // target
    Long mno = 24L;
    // When
    List<Order> orders = service.getOrdersByMember(mno);
    // then
    assertNotNull(orders);
  }

  @Test
  public void getOrderHistoryTest() {
    // target
    Long mno = 24L;
    // When
    List<OrderHistoryDto> orders = service.getOrderHistory(mno);
    // then
    assertNotNull(orders);
  }

}
