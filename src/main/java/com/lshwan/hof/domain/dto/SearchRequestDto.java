package com.lshwan.hof.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchRequestDto {
  private String tableName;
  private List<String> searchColumns; // 제목+내용
  private String keyword;
  private String sortColumn; //정렬할 컬럼 (ex)일자,번호 )
  private String sortOrder; //정렬방향
  private Long category;
}
