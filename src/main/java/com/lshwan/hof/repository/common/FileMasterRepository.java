package com.lshwan.hof.repository.common;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.common.FileMaster.FileType;
import com.lshwan.hof.domain.entity.note.Note;

import jakarta.transaction.Transactional;

public interface FileMasterRepository extends JpaRepository<FileMaster,String>{
  
  List<FileMaster> findByProdPnoAndFileType(Long pno, FileType fileType);

  @Transactional
  @Modifying
  @Query("DELETE FROM FileMaster f WHERE f.prod.pno = :pno")
  void deleteByProdNo(@Param("pno") Long pno);

  // 특정 공지사항(팝업)과 연결된 파일 리스트 조회
  List<FileMaster> findByNotice_No(Long noticeNo);

  //  fileUrl로 FileMaster 찾기
  Optional<FileMaster> findByUrl(String url);
    // 다중 URL 조회를 위한 메서드 추가
    List<FileMaster> findByUrlIn(List<String> urls);
    // 특정 Note에 연결된 이미지 목록 조회
  List<FileMaster> findByNote(Note note);
}
