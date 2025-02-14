package com.lshwan.hof.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lshwan.hof.domain.dto.SearchRequestDto;
import com.lshwan.hof.service.util.SearchService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping
    public List<Map<String, Object>> search(@RequestBody SearchRequestDto request) {
        return searchService.search(request);
    }
}