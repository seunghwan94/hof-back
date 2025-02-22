package com.lshwan.hof.repository.note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.note.Reply;


public interface ReplyRepository extends JpaRepository<Reply,Long>{
  
    // 특정 게시글의 댓글 조회 (대댓글 포함)
    List<Reply> findByNoteNnoOrderByReplyOrderAsc(Long nno);

    // 특정 사용자의 댓글 조회
    List<Reply> findByMemberMno(Long mno);

    // 부모 댓글로 대댓글 조회
    List<Reply> findByParentReplyNo(Long parentNo);

    // 삭제되지 않은 댓글 조회
    List<Reply> findByIsDeletedFalseAndNoteNno(Long nno);
}
