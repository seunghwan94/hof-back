package com.lshwan.hof.repository.prod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.prod.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
  // 회원 번호로 장바구니 항목 조회
  List<Cart> findAllByMember_Mno(Long mno);
}
