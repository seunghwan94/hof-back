package com.lshwan.hof.service.prod;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.prod.CartDto;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
public class CartServiceTests {

  @Autowired
  private CartService service;

  /**
   * 장바구니 담기 테스트
   */
  @Test
  void addToCartTest() {
      CartDto cartDto = CartDto.builder()
              .mno(23L) // 회원 번호
              .pno(406L) // 상품 번호
              .build();

      CartDto savedCart = service.addToCart(cartDto);

      assertNotNull(savedCart, "장바구니에 담긴 항목이 null이 아닙니다.");
    }

  /**
   * 장바구니 조회 테스트
   */
  @Test
  void getCartItemsTest() {
      Long mno = 23L; // 테스트용 회원 번호

      List<CartDto> cartItems = service.getCartItems(mno);

      assertNotNull(cartItems, "장바구니 항목 리스트가 null이 아닙니다.");
      log.info("회원 {}의 장바구니 항목 수: {}", mno, cartItems.size());

      cartItems.forEach(item -> log.info("장바구니 항목: {}", item));
  }

  /**
   * 장바구니 항목 수정 테스트
   */
  // @Test
  // void updateCartItemTest() {
  //   // target
  //   CartDto cartDto = CartDto.builder()
  //             .mno(23L) // 회원 번호
  //             .pno(406L) // 상품 번호
  //           .build();
  //   CartDto savedCart = service.addToCart(cartDto);
  //   // given
  //   CartDto updatedCartDto = CartDto.builder()
  //             .mno(23L) 
  //             .pno(406L) 
  //             .count(4) 
  //           .build();
  //   // 서비스 호출 - 장바구니 항목 수정
  //   CartDto updatedCart = service.updateCartItem(savedCart.getCartNo(), updatedCartDto);
  //   // 검증: 수정된 항목이 null이 아닌지 확인
  //   assertNotNull(updatedCart);
  // }

  // /**
  //  * 장바구니 항목 삭제 테스트
  //  */
  // @Test
  // void deleteCartItemTest() {
  //   Long cartId = 4L; // 삭제할 장바구니 항목의 ID
  //   service.deleteCartItem(cartId);
  // }

  /**
   * 장바구니 담기 후 조회 테스트
   */
  @Test
  void addAndGetCartItemsTest() {
    // 장바구니 담기
    CartDto cartDto = CartDto.builder()
            .mno(23L)
            .pno(406L)
          .build();

    CartDto savedCart = service.addToCart(cartDto);
    assertNotNull(savedCart, "장바구니에 항목 추가 실패");

    // 장바구니 조회
    List<CartDto> cartItems = service.getCartItems(23L);
    assertTrue(cartItems.size() > 0, "장바구니에 최소 1개의 항목이 있어야 합니다.");

  }
}
