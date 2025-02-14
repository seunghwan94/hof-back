package com.lshwan.hof.service.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.SearchRequestDto;
import com.lshwan.hof.mapper.util.SearchMapper;

@Service
public class SearchServiceImpl implements SearchService{
  @Autowired
  SearchMapper mapper;
  @Override
  public List<Map<String, Object>> search(SearchRequestDto request) {
    return mapper.search(request);
  }
  
}
