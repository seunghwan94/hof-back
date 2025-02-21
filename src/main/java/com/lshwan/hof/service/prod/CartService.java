package com.lshwan.hof.service.prod;


import java.util.List;

import com.lshwan.hof.domain.dto.prod.CartDto;

public interface CartService {
  // 1. 장바구니 담기
  CartDto addToCart(CartDto cartDto);

  // // 2. 장바구니 조회
  List<CartDto> getCartItems(Long mno);

  // // 3. 장바구니 항목 수정
  CartDto updateCartItem(Long cartId, CartDto cartDto);

  // 4. 장바구니 항목 삭제
  void deleteCartItem(Long cartId);
}
