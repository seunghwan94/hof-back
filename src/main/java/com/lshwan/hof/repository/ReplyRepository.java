package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long>{
  
}
