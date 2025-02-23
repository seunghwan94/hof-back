package com.lshwan.hof.repository.note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.entity.note.Reply;


public interface ReplyRepository extends JpaRepository<Reply,Long>{
  
    // 특정 게시글의 댓글 (정렬 포함)
    @Query("SELECT r FROM Reply r WHERE r.note.nno = :nno ORDER BY r.parentReply.no NULLS FIRST, r.regDate ASC")
    List<Reply> findByNoteNnoOrdered(@Param("nno") Long nno);

    // 특정 게시글의 댓글 수
    int countByNoteNno(Long nno);

    // 특정 부모 댓글의 대댓글 조회
    List<Reply> findByParentReplyNo(Long parentNo);
}
