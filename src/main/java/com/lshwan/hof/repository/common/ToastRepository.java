package com.lshwan.hof.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lshwan.hof.domain.entity.common.ToastEntity;


public interface ToastRepository extends JpaRepository<ToastEntity,Long> {
  
}
