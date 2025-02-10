package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.qna.Qna;

public interface QnaRepository extends JpaRepository<Qna,Long>{
  
}
