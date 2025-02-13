package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.prod.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
  
}
