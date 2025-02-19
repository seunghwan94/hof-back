package com.lshwan.hof.service.pay;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.order.OrderDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.order.Delivery;
import com.lshwan.hof.domain.entity.order.Order;
import com.lshwan.hof.domain.entity.order.OrderItem;
import com.lshwan.hof.domain.entity.order.Delivery.DeliveryStatus;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.order.DeliveryRepository;
import com.lshwan.hof.repository.order.OrderItemRepository;
import com.lshwan.hof.repository.order.OrderRepository;
import com.lshwan.hof.repository.prod.ProdOptionRepository;
import com.lshwan.hof.repository.prod.ProdRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final MemberRepository memberRepository;
  private final ProdRepository prodRepository;
  private final ProdOptionRepository prodOptionRepository;
  private final DeliveryRepository deliveryRepository;

  /**
   * 주문 생성
   */
  @Transactional
  public Order createOrder(OrderDto orderDto) {
    // 1. 회원 조회
    Member member = memberRepository.findById(orderDto.getMno())
      .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

    // 2. 주문 객체 생성
    Order order = orderRepository.save(Order.builder().member(member).totalPrice(0).build());

    // 3. 주문 아이템 생성 및 총 가격 계산
        List<OrderItem> orderItems = orderDto.getItems().stream()
      .map(itemDto -> {

        // 상품 조회 추가
        Prod prod = prodRepository.findById(itemDto.getPno())
          .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 옵션 조회 (옵션이 있는 경우)
        ProdOption prodOption = null;
        if (itemDto.getOptionNo() != null) {
            prodOption = prodOptionRepository.findById(itemDto.getOptionNo())
                    .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));
        }

        return  OrderItem.builder()
                  .order(order)
                  .prod(prod) // 상품 설정
                  .prodOption(prodOption) // 옵션 설정
                  .count(1)
                  .basePrice(itemDto.getBasePrice())
                  .subtotalPrice(itemDto.getBasePrice() * itemDto.getCount())
                .build();

      }).collect(Collectors.toList());

    orderItemRepository.saveAll(orderItems);
    
    // 4. 총 가격 계산
    int totalPrice = orderItems.stream()
        .mapToInt(OrderItem::getSubtotalPrice)
      .sum();

    // 5. setter 사용해서 totalPrice 변경
    order.setTotalPrice(totalPrice); 

    return order; // `@Transactional` 덕분에 order는 자동으로 업데이트됨!
  }
    
  /**
   * 배송상태 생성
   */
  public Order addDelivery(Long orderNo) { 
    if (orderNo == null) throw new IllegalStateException("Order가 먼저 저장되지 않았습니다.");
    Order order = orderRepository.findById(orderNo).get();

    Delivery delivery = deliveryRepository.save(Delivery.builder().status(DeliveryStatus.상품준비중).build());
    order.setDelivery(delivery);
    orderRepository.save(order);

    return order;
  }

  /**
   * 특정 주문 조회 (주문번호 기반)
   */
  public Order getOrderById(Long orderNo) { 
    return orderRepository.findById(orderNo)
      .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));
  }

  /**
   * 특정 회원의 주문 목록 조회
   */
  public List<Order> getOrdersByMember(Long mno) {
    return orderRepository.findByMember_Mno(mno);
  }

}
