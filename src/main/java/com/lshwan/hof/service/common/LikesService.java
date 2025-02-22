package com.lshwan.hof.service.common;

import com.lshwan.hof.domain.entity.common.Likes;

public interface LikesService {
  // 좋아요 추가
  void add(Long mno, Long targetNo, Likes.TargetType targetType);
  // 좋아요 취소
  void remove(Long mno, Long targetNo, Likes.TargetType targetType);
  // 좋아요 수 조회
  long countLikes(Long targetNo, Likes.TargetType targetType);
}
