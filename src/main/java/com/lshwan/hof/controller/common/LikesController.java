package com.lshwan.hof.controller.common;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.lshwan.hof.domain.dto.common.LikeProdDto;
import com.lshwan.hof.domain.dto.common.LikeDto;
import com.lshwan.hof.domain.entity.common.Likes;
import com.lshwan.hof.service.common.LikesService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@RequestMapping("common/likes")
@Log4j2
public class LikesController {
  private final LikesService likesService;

  // 좋아요 추가
  @PostMapping
  public ResponseEntity<?> addLike(@RequestBody LikeDto likeDto) {
    LikeDto createdLike = likesService.add(
                            likeDto.getMno(),
                            likeDto.getTargetNo(),
                            Likes.TargetType.valueOf(likeDto.getTargetType())
                          );
    return ResponseEntity.status(HttpStatus.CREATED).body(createdLike);
  }

  // 좋아요 취소
  @DeleteMapping
  public ResponseEntity<?> removeLike(@RequestParam(name = "mno") Long mno,
                                      @RequestParam(name = "targetNo") Long targetNo,
                                      @RequestParam(name = "targetType") Likes.TargetType targetType) {
                                        log.info("mno : " + mno);
                                        log.info("targetNo : " + targetNo);
                                        log.info("targetType : " + targetType);
    likesService.remove(mno, targetNo, targetType);
    return ResponseEntity.noContent().build();
  }

  // 좋아요 수 조회
  @GetMapping("/count")
  public ResponseEntity<?> countLikes(@RequestParam(name = "targetNo") Long targetNo,
                                      @RequestParam(name = "targetType") Likes.TargetType targetType) {
    long count = likesService.countLikes(targetNo, targetType);
    return ResponseEntity.ok(count);
  }

  // 좋아요 내가 누른지 확인
  @GetMapping("/user-liked")
  public ResponseEntity<?> userLiked(@RequestParam(name = "mno") Long mno,
                                     @RequestParam(name = "targetNo") Long targetNo,
                                     @RequestParam(name = "targetType") Likes.TargetType targetType) {
                                      log.info("mno : " + mno);
                                      log.info("targetNo : " + targetNo);
                                      log.info("targetType : " + targetType);
      boolean liked = likesService.findBy(mno, targetNo, targetType);
      return ResponseEntity.ok(liked);
  }
  
  @GetMapping("/list")
  public ResponseEntity<List<LikeProdDto>> getLikedProducts(@RequestParam(name = "mno") Long mno) {
      List<LikeProdDto> likedProducts = likesService.getLikedProducts(mno);
      return ResponseEntity.ok(likedProducts);
  }
}
