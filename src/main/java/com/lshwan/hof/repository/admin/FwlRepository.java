package com.lshwan.hof.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lshwan.hof.domain.entity.admin.FWL;

@Repository
public interface FwlRepository extends JpaRepository<FWL,Long>{
  
}
