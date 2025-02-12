package com.lshwan.hof.repository.prod;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.prod.Prod;

public interface ProdRepository extends JpaRepository<Prod,Long>{
  Optional<Prod> findByTitle(String title);
}
