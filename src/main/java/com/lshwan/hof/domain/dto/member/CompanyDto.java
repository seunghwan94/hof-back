package com.lshwan.hof.domain.dto.member;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

  private Long no;
  private String name;
  private String title;
  private String info;
  private String content;
  private String tel;
  private int count;
  private Long memberNo;  // 연관된 Member의 식별자

  // 저장된 이미지 URL 목록
  private List<String> imageUrls;
}
