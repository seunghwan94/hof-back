package com.lshwan.hof.repository.note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.entity.note.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{
  // 특정 사용자의 게시글 목록 조회
  List<Note> findByMemberMno(Long mno);

  // 제목 또는 내용으로 검색
  @Query("SELECT n FROM Note n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword%")
  List<Note> searchByKeyword(@Param("keyword") String keyword);

  // 삭제되지 않은 게시글만 조회
  List<Note> findByIsDeletedFalse();
}