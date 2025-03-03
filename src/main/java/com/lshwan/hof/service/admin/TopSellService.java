package com.lshwan.hof.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lshwan.hof.mapper.TopSellMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TopSellService {
  private final TopSellMapper sellMapper;

  public List<Map<String, Object>> getTopSellingProducts() {
      return sellMapper.getTopSellingProducts();
  }
}
