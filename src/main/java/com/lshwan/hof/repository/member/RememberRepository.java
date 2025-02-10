package com.lshwan.hof.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.Remember;

public interface RememberRepository extends JpaRepository<Remember, Long>{
  
}
