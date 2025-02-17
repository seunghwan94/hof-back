package com.lshwan.hof.domain.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(exclude = "dtoList")
public class PageResultDto<D,E> {
  private List<D> dtoList;

  private int totalPage;//총 갯수
  private int page, size;//볼 페이지와 사이즈
  private int start, end;//하단의 페이지 넘버링
  private boolean prev,next; //하단의 페이지 버튼

  private List<Integer> pageList; //스타트 앤드값을가진 페이지번호리스트


  public PageResultDto(Page<E> result, Function<E,D> fn){
    dtoList = result.stream().map(fn).toList();
    totalPage = result.getTotalPages();

    Pageable pageable = result.getPageable();
    page = pageable.getPageNumber() +1 ;
    size = pageable.getPageSize();

    int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;
    start = tempEnd - 9;
    prev = start > 1;

    end = totalPage > tempEnd ? tempEnd : totalPage;
    next = totalPage > tempEnd;

    pageList = IntStream.rangeClosed(start, end).boxed().toList();
  }
}
