package com.lshwan.hof.repository.prod.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lshwan.hof.domain.entity.prod.view.ProdView;
import java.util.List;


public interface ProdViewRepository extends JpaRepository<ProdView, Long>{
  
  @Query("SELECT p FROM ProdView p WHERE p.title LIKE %:title%")
  List<ProdView> findByTitle(String title);

  List<ProdView> findByCno(Long cno);
}
