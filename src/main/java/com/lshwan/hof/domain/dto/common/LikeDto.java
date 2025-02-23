package com.lshwan.hof.domain.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {
    private Long mno;            // 좋아요를 누른 사용자 ID
    private Long targetNo;       // 좋아요 대상 ID (게시글, 댓글 등)
    private String targetType;   // 대상 타입 (예: NOTE, REPLY)
    private String likedAt;      // 좋아요 누른 시간
}
