package com.lshwan.hof.repository.prod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.prod.ProdOption;

public interface ProdOptionRepository extends JpaRepository<ProdOption,Long>{
  List<ProdOption> findByOptionMapsProdPno(Long pno);
}
