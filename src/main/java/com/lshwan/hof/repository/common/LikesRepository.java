package com.lshwan.hof.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.common.Likes;

public interface LikesRepository extends JpaRepository<Likes,Likes.LikesId>{
  
}
