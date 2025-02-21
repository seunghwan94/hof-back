package com.lshwan.hof.controller.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.prod.CartDto;
import com.lshwan.hof.service.prod.CartService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@RequestMapping("main/cart")
@Log4j2
public class CartController {
      private final CartService cartService;

    /**
     * 1. 장바구니 담기
     * POST /api/cart
     */
    @PostMapping
    public ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto) {
        log.info("장바구니 담기 요청: {}", cartDto);
        CartDto savedCart = cartService.addToCart(cartDto);
        return ResponseEntity.ok(savedCart);
    }

    /**
     * 2. 장바구니 조회
     * GET /api/cart/{mno}
     */
    @GetMapping("/{mno}")
    public ResponseEntity<List<CartDto>> getCartItems(@PathVariable(name = "mno") Long mno) {
      log.info("장바구니 조회 요청 - 회원 번호: {}", mno);
      List<CartDto> cartItems = cartService.getCartItems(mno);
      return ResponseEntity.ok(cartItems);
    }

    /**
     * 3. 장바구니 항목 수정
     * PUT /api/cart/{cartId}
     */
    @PutMapping("/{cartId}")
    public ResponseEntity<CartDto> updateCartItem(@PathVariable(name = "cartId") Long cartId, @RequestBody CartDto cartDto) {
      log.info("장바구니 항목 수정 요청 - cartId: {}, 수정 데이터: {}", cartId, cartDto);
      CartDto updatedCart = cartService.updateCartItem(cartId, cartDto);
      return ResponseEntity.ok(updatedCart);
    }

    /**
     * 4. 장바구니 항목 삭제
     * DELETE /api/cart/{cartId}
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable(name = "cartId") Long cartId) {
        log.info("장바구니 항목 삭제 요청 - cartId: {}", cartId);
        cartService.deleteCartItem(cartId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/all/{mno}")
    public ResponseEntity<Void> deleteCartItemList(@PathVariable(name = "mno") Long mno) {
        log.info("장바구니 항목 삭제 요청 - mno: {}", mno);
        cartService.deleteCartItemList(mno);
        return ResponseEntity.noContent().build();
    }
    /**
     * 5. 장바구니 임시저장
     * PUT /api/cart/save
     */
    @PutMapping("/save")
    public ResponseEntity<Void> saveCart(@RequestBody List<CartDto> cartItems) {
        log.info("장바구니 임시저장 요청: {}", cartItems);
        cartService.saveCartItems(cartItems);
        return ResponseEntity.ok().build();
    }
}
