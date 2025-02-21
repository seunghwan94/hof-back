package com.lshwan.hof.domain.dto.note;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private Long no;
    private String memberId;
    private String title;
    private String content;
    private String clickUrl;
    private String backgroundColor;
    private String fileUrl;

    
}
