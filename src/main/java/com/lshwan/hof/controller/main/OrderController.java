package com.lshwan.hof.controller.main;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody; 

import com.lshwan.hof.domain.dto.order.OrderDto;
import com.lshwan.hof.domain.dto.order.OrderHistoryDto;
import com.lshwan.hof.domain.entity.order.Order;
import com.lshwan.hof.service.pay.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@RequestMapping("main/order")
@Log4j2
public class OrderController {

    private final OrderService orderService;

    /**
     * 1️⃣ 주문 생성 (결제 전)
     */
    @PostMapping("/create")
    @Operation(summary = "주문 추가 API", description = "주문 내역에 추가합니다")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        // if (orderDto.getMno() == null) {
        //     return ResponseEntity.badRequest().body("❌ 회원 번호(mno)가 누락되었습니다.");
        // }
        log.info("orderDto : " + orderDto);
        Order createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }

    /**
     * 2️⃣ 특정 주문 조회
     */
    @GetMapping("/{orderNo}")
    @Operation(summary = "주문 상세정보 API", description = "특정 주문을 조회합니다")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderNo) {
        Order order = orderService.getOrderById(orderNo);
        return ResponseEntity.ok(order);
    }

    /**
     * 3️⃣ 특정 회원의 주문 목록 조회
     */
    @GetMapping("/member/{mno}")
    @Operation(summary = "주문 목록 List API", description = "특정 회원의 주문 목록을 조회합니다")
    public ResponseEntity<List<Order>> getOrdersByMember(@PathVariable Long mno) {
        List<Order> orders = orderService.getOrdersByMember(mno);
        return ResponseEntity.ok(orders);
    }

    /**
     * 구매내역 조회 API
     * @param mno 회원 번호
     * @return 회원의 구매내역 리스트
     */
    @GetMapping("/history/{mno}")
    @Operation(summary = "상품 판매량 조회API", description = "dashboard 의 차트에 쓸 상품 판매량 데이터를 조회합니다")
    public ResponseEntity<List<OrderHistoryDto>> getOrderHistory(@PathVariable(name = "mno") Long mno) {
        List<OrderHistoryDto> orderHistory = orderService.getOrderHistory(mno);
        return ResponseEntity.ok(orderHistory);
    }
}
