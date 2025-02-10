package com.lshwan.hof.repository.social;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.social.MFA;

public interface MFARepository extends JpaRepository<MFA, Long>{
  
}
