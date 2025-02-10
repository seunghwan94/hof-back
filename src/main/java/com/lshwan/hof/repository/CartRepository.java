package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
  
}
