package com.lshwan.hof.repository.prod;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.prod.ProdOption;

public interface ProdOptionRepository extends JpaRepository<ProdOption,Long>{
  
}
