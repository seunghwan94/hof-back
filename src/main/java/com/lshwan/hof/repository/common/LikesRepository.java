package com.lshwan.hof.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lshwan.hof.domain.entity.common.Likes;

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
}
