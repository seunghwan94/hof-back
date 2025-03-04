package com.lshwan.hof.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lshwan.hof.domain.dto.SearchRequestDto;
import com.lshwan.hof.service.util.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping
    @Operation(summary = "검색 공용 코드 API", description = "리퀘스트 파라미터에따라서 모든테이블과 정해진 컬럼에 따라 조회합니다")
    public List<Map<String, Object>> search(@RequestBody SearchRequestDto request) {
        return searchService.search(request);
    }
}