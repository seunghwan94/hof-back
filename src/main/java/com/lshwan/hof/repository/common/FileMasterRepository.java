package com.lshwan.hof.repository.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.common.FileMaster.FileType;

import jakarta.transaction.Transactional;

public interface FileMasterRepository extends JpaRepository<FileMaster,String>{
  List<FileMaster> findByProdPnoAndFileType(Long pno, FileType fileType);

  @Transactional
  @Modifying
  @Query("DELETE FROM FileMaster f WHERE f.prod.pno = :pno")
  void deleteByProdNo(@Param("pno") Long pno);
}
