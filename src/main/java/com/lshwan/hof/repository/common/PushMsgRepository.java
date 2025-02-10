package com.lshwan.hof.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.common.PushMsg;

public interface PushMsgRepository extends JpaRepository<PushMsg,Long>{
  
}
