package com.lshwan.hof.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.service.admin.CategorySalesService;
import com.lshwan.hof.service.admin.TopSellService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/dashboard")
@RequiredArgsConstructor

public class Dashboard {
  private final CategorySalesService categorySalesService;
  @Autowired
  private TopSellService sellService;

  @GetMapping("/category")
  public List<Map<String, Object>> getCategorySales() {
      return categorySalesService.getCategorySales();
  }
  @GetMapping("/topsell")
  public List<Map<String, Object>> getTopSellingProducts() {
      return sellService.getTopSellingProducts();
  }

}
