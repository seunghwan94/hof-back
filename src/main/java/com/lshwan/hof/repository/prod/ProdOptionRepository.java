package com.lshwan.hof.repository.prod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lshwan.hof.domain.entity.prod.ProdOption;

import jakarta.transaction.Transactional;

public interface ProdOptionRepository extends JpaRepository<ProdOption,Long>{
  List<ProdOption> findByOptionMapsProdPno(Long pno);


  @Transactional
  @Modifying
  @Query("DELETE FROM ProdOption po WHERE NOT EXISTS (SELECT 1 FROM ProdOptionMap pom WHERE pom.option = po)")
  void deleteOrphanOptions(Long pno);
  
}
