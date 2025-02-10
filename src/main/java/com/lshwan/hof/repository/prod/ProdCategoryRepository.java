package com.lshwan.hof.repository.prod;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.prod.ProdCategory;

public interface ProdCategoryRepository extends JpaRepository<ProdCategory,Long>{
  
  
}
