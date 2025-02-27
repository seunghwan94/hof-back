package com.lshwan.hof.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.entity.common.Likes;
import com.lshwan.hof.domain.entity.prod.view.ProdView;

import jakarta.transaction.Transactional;

public interface LikesRepository extends JpaRepository<Likes,Likes.LikesId>{
      // 특정 대상에 대한 좋아요 수
    @Query("SELECT COUNT(l) FROM Likes l WHERE l.id.targetNo = :targetNo AND l.id.targetType = :targetType")
    long countByTarget(@Param("targetNo") Long targetNo, @Param("targetType") Likes.TargetType targetType);

    // 특정 사용자가 특정 대상에 좋아요를 눌렀는지 여부
    boolean existsById(Likes.LikesId id);

    // 특정 대상의 모든 좋아요 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM Likes l WHERE l.id.targetNo = :targetNo AND l.id.targetType = :targetType")
    void deleteByTarget(@Param("targetNo") Long targetNo, @Param("targetType") Likes.TargetType targetType);

    // Native Query로 좋아요 여부 확인
    @Query(value = "SELECT EXISTS (SELECT 1 FROM tbl_likes WHERE mno = :mno AND target_no = :targetNo AND target_type = BINARY :targetType)", nativeQuery = true)
    Integer existsLike(@Param("mno") Long mno, @Param("targetNo") Long targetNo, @Param("targetType") String targetType);

    @Query("SELECT p FROM ProdView p WHERE p.pno IN (SELECT l.id.targetNo FROM Likes l WHERE l.id.member = :mno AND l.id.targetType = 'FAV')")
    List<ProdView> findLikedProducts(@Param("mno") Long mno);
    
}
