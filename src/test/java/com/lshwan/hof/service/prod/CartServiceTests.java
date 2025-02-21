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
     * 1️⃣ 장바구니 담기 테스트
     */
    @Test
    void addToCartTest() {
        CartDto cartDto = CartDto.builder()
                .mno(23L) // 회원 번호
                .pno(406L) // 상품 번호
                .build();

        CartDto savedCart = service.addToCart(cartDto);

        assertNotNull(savedCart, "장바구니에 담긴 항목이 null이 아닙니다.");
        assertEquals(23L, savedCart.getMno(), "회원 번호가 일치해야 합니다.");
        assertEquals(406L, savedCart.getPno(), "상품 번호가 일치해야 합니다.");

        log.info("장바구니 담기 성공: {}", savedCart);
    }

    /**
     * 2️⃣ 장바구니 조회 테스트
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
     * 3️⃣ 장바구니 항목 수정 테스트
     */
    @Test
    void updateCartItemTest() {
      Long cartId = 4L; // 수정할 장바구니 항목 ID

      // 수정할 데이터 생성 (상품 변경 및 옵션 포함)
      CartDto updatedCartDto = CartDto.builder()
              .mno(23L) // 회원 번호
              .pno(406L) // 새로운 상품 번호
              .count(4) // 수량 수정
              .build();

      // 서비스 호출 - 장바구니 항목 수정
      CartDto updatedCart = service.updateCartItem(cartId, updatedCartDto);

      // 검증: 수정된 항목이 null이 아닌지 확인
      assertNotNull(updatedCart, "수정된 장바구니 항목이 null이 아닙니다.");

      // 검증: 수정된 상품 번호와 일치하는지 확인
      assertEquals(updatedCartDto.getPno(), updatedCart.getPno(), "수정된 상품 번호가 일치해야 합니다.");

      // 검증: 수량이 올바르게 반영되었는지 확인
      assertEquals(updatedCartDto.getCount(), updatedCartDto.getCount(), "수량이 올바르게 수정되어야 합니다.");

      // 로그 출력
      log.info("장바구니 항목 수정 성공: {}", updatedCart);
    }

    /**
     * 4️⃣ 장바구니 항목 삭제 테스트
     */
    @Test
    void deleteCartItemTest() {
        Long cartId = 4L; // 삭제할 장바구니 항목의 ID

        // 삭제 시 예외가 발생하지 않아야 함
        assertDoesNotThrow(() -> service.deleteCartItem(cartId), "장바구니 항목 삭제 중 예외 발생");

        log.info("장바구니 항목 삭제 성공 - cartId: {}", cartId);
    }

    /**
     * 5️⃣ 장바구니 담기 후 조회 테스트
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

        log.info("추가 후 장바구니 조회 성공: {}", cartItems);
    }
}
