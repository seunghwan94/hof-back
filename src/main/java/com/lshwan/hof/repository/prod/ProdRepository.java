package com.lshwan.hof.repository.prod;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.prod.Prod;

public interface ProdRepository extends JpaRepository<Prod,Long>{
  
}
