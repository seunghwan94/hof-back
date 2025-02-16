package com.lshwan.hof.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.common.FileMaster.FileType;

public interface FileMasterRepository extends JpaRepository<FileMaster,String>{
  List<FileMaster> findByProdPnoAndFileType(Long pno, FileType fileType);
}
