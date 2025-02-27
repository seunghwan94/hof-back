package com.lshwan.hof.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.entity.common.Qna;

public interface QnaRepository extends JpaRepository<Qna,Long>{
  @Modifying
  @Query("DELETE FROM Qna q WHERE q.parentQna.no = :parentNo")
  void deleteByParentNo(@Param("parentNo") Long parentNo);

  @Query("SELECT q FROM Qna q WHERE q.pno.pno = :pno ORDER BY q.parentQna.no NULLS FIRST, q.no ASC")
  List<Qna> findByPno(@Param("pno") Long pno);
}
