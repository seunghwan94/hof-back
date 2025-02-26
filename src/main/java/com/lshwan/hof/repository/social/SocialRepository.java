package com.lshwan.hof.repository.social;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.social.Social;

public interface SocialRepository extends JpaRepository<Social, Long>{
  // Optional<Social> findById(String id); // 소셜 로그인 ID로 사용자 찾기
  // Optional<Social> findByEmail(String email); // 이메일로 소셜 사용자 찾기
  Social findByEmail(String email); // 이메일로 소셜 사용자 찾기
  Social findById(String id);
}
