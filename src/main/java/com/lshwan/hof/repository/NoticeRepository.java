package com.lshwan.hof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.notice.Notice;

public interface NoticeRepository extends JpaRepository<Notice,Long>{
  
}
