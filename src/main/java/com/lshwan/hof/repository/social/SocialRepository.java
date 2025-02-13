package com.lshwan.hof.repository.social;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.social.Social;

public interface SocialRepository extends JpaRepository<Social, Long>{
  
}
