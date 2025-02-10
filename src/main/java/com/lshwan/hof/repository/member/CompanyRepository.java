package com.lshwan.hof.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.member.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
  
}
