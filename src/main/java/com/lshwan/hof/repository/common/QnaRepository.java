package com.lshwan.hof.repository.common;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lshwan.hof.domain.entity.common.Qna;

public interface QnaRepository extends JpaRepository<Qna,Long>{
  @Modifying
  @Query("DELETE FROM Qna q WHERE q.parentQna.no = :parentNo")
  void deleteByParentNo(@Param("parentNo") Long parentNo);
}
