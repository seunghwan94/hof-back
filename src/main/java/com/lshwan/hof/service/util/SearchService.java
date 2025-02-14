package com.lshwan.hof.service.util;

import java.util.List;
import java.util.Map;

import com.lshwan.hof.domain.dto.SearchRequestDto;

public interface SearchService {
  List<Map<String, Object>> search(SearchRequestDto request);
}
