package com.lshwan.hof.repository.prod;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lshwan.hof.domain.entity.prod.ProdOptionMap;

import jakarta.transaction.Transactional;

public interface ProdOptionMapRepository extends JpaRepository<ProdOptionMap,Long>{

  @Transactional
  @Modifying
  @Query("DELETE FROM ProdOptionMap pom WHERE pom.option.no = :ono") 
  void deleteByOptionNo(@Param("ono") Long ono);

  
    
  
}
