package com.lshwan.hof.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.PwChanged;

public interface PwChangedRepository extends JpaRepository<PwChanged, Long>{
  
}
