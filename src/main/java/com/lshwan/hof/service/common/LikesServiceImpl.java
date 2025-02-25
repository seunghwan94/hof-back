package com.lshwan.hof.service.common;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.common.LikeDto;
import com.lshwan.hof.domain.entity.common.Likes;
import com.lshwan.hof.domain.entity.common.Likes.TargetType;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.common.LikesRepository;
import com.lshwan.hof.repository.member.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class LikesServiceImpl implements LikesService{
  private final LikesRepository likesRepository;
  private final MemberRepository memberRepository;

  @Override
  public LikeDto add(Long mno, Long targetNo, TargetType targetType) {
   Likes.LikesId likesId = new Likes.LikesId(mno, targetNo, targetType);

        if (likesRepository.existsById(likesId)) {
            throw new IllegalStateException("Already liked");
        }

        Member member = memberRepository.findById(mno)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        Likes like = Likes.builder()
                .id(likesId)
                .member(member)
                .build();

        likesRepository.save(like);

        return LikeDto.builder()
                .mno(mno)
                .targetNo(targetNo)
                .targetType(targetType.name())
                .likedAt(LocalDateTime.now().toString())
                .build();
  }
  // 좋아요 수 조회
  @Override
  public long countLikes(Long targetNo, TargetType targetType) {
    return likesRepository.countByTarget(targetNo, targetType);
  }

  // 좋아요 취소    
  @Override
  public void remove(Long mno, Long targetNo, TargetType targetType) {
    Likes.LikesId likesId = new Likes.LikesId(mno, targetNo, targetType);

    Likes like = likesRepository.findById(likesId)
            .orElseThrow(() -> new EntityNotFoundException("Like not found"));

    likesRepository.delete(like);
  }

  @Override
  public boolean findBy(Long mno, Long targetNo, TargetType targetType) {
    Integer result = likesRepository.existsLike(mno, targetNo, targetType);
    return result != null && result == 1;
  }
  
}
