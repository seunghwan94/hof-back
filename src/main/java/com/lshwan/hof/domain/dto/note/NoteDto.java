package com.lshwan.hof.domain.dto.note;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
public class NoteDto {
    private Long mno; // 작성자 ID
    private String title;
    private String content;

    // 이미지 파일 업로드를 위한 필드
    private List<MultipartFile> images;

    // 저장된 이미지 URL 목록
    private List<String> imageUrls;
}
