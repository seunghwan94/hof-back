// package com.lshwan.hof.service.pay;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.lshwan.hof.domain.dto.order.OrderDto;
// import com.lshwan.hof.domain.dto.order.OrderItemDto;
// import com.lshwan.hof.domain.entity.order.Order;

// import jakarta.transaction.Transactional;
// import lombok.extern.log4j.Log4j2;

// @SpringBootTest
// @Log4j2
// @Transactional
// public class OrderServiceTests {
//       @Autowired
//     private OrderServiceImpl orderService; // 실제 Bean 주입

//     @Test
//     public void 주문_추가_테스트() {
//         // Given (주문 데이터 준비)
//         OrderDto orderDto = OrderDto.builder()
//                 .mno(23L) // 회원번호 (실제 DB에 존재하는 회원이어야 함)
//                 .items(List.of(
//                         new OrderItemDto(406L, 2, 10000, 20000),
//                         new OrderItemDto(407L, 1, 5000, 5000)
//                 ))
//                 .build();

//         // When (주문 생성)
//         Order savedOrder = orderService.createOrder(orderDto);

//         // Then (검증)
//         assertNotNull(savedOrder);
//         assertTrue(savedOrder.getNo() > 0);
//         assertEquals(25000, savedOrder.getTotalPrice()); // 총 가격 검증
//         log.info("주문 번호: {}", savedOrder.getNo());
//     }

//     @Test
//     public void 회원_주문_조회_테스트() {
//         // Given (테스트를 위한 회원번호)
//         Long mno = 23L;

//         // When (해당 회원의 주문 조회)
//         List<Order> orders = orderService.getOrdersByMember(mno);

//         // Then (검증)
//         assertNotNull(orders);
//         assertFalse(orders.isEmpty());
//         log.info("조회된 주문 수: {}", orders.size());
//     }

//     // @Test
//     // public void 주문_수정_테스트() {
//     //     // Given (새로운 주문 생성)
//     //     OrderDto orderDto = OrderDto.builder()
//     //             .mno(23L)
//     //             .items(List.of(
//     //                     new OrderItemDto(10L, 1, 10000, 10000)
//     //             ))
//     //             .build();

//     //     Order order = orderService.createOrder(orderDto);
//     //     assertNotNull(order);

//     //     // When (주문 수정 - 총 가격 변경)
//     //     order.setTotalPrice(50000);
//     //     Order updatedOrder = orderService.createOrder(orderDto);

//     //     // Then (검증)
//     //     assertNotNull(updatedOrder);
//     //     assertEquals(50000, updatedOrder.getTotalPrice());
//     // }

//     // @Test
//     // public void 주문_삭제_테스트() {
//     //     // Given (새로운 주문 생성)
//     //     OrderDto orderDto = OrderDto.builder()
//     //             .mno(1L)
//     //             .items(List.of(
//     //                     new OrderItemDto(10L, 1, 10000, 10000)
//     //             ))
//     //             .build();

//     //     Order order = orderService.createOrder(orderDto);
//     //     assertNotNull(order);

//     //     // When (주문 삭제)
//     //     boolean isRemoved = orderService.removeOrder(order.getNo());

//     //     // Then (검증)
//     //     assertTrue(isRemoved);
//     // }
// }
