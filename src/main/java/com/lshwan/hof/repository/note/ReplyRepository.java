package com.lshwan.hof.repository.note;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.note.Reply;


public interface ReplyRepository extends JpaRepository<Reply,Long>{
  
}
