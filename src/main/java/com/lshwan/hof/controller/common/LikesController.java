package com.lshwan.hof.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.entity.common.Likes;
import com.lshwan.hof.service.common.LikesService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("common/likes")
public class LikesController {
  private final LikesService likesService;

  // 좋아요 추가
  @PostMapping
  public ResponseEntity<Void> addLike(@RequestParam Long mno,
                                      @RequestParam Long targetNo,
                                      @RequestParam Likes.TargetType targetType) {
    likesService.add(mno, targetNo, targetType);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // 좋아요 취소
  @DeleteMapping
  public ResponseEntity<Void> removeLike(@RequestParam Long mno,
                                          @RequestParam Long targetNo,
                                          @RequestParam Likes.TargetType targetType) {
    likesService.remove(mno, targetNo, targetType);
    return ResponseEntity.noContent().build();
  }

  // 좋아요 수 조회
  @GetMapping("/count")
  public ResponseEntity<Long> countLikes(@RequestParam Long targetNo,
                                         @RequestParam Likes.TargetType targetType) {
    long count = likesService.countLikes(targetNo, targetType);
    return ResponseEntity.ok(count);
  }
}
