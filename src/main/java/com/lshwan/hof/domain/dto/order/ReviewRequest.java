package com.lshwan.hof.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long reviewId; 
    private Long mno;
    private Long pno;
    private String content;
    private String star;
}