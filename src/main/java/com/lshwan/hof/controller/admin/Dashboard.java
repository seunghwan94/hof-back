package com.lshwan.hof.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.service.admin.CategorySalesService;
import com.lshwan.hof.service.admin.TopSellService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/dashboard")
@RequiredArgsConstructor

public class Dashboard {
  private final CategorySalesService categorySalesService;
  @Autowired
  private TopSellService sellService;

  @GetMapping("/category")
    @Operation(summary = "대쉬보드의 카테고리별 판매 조회", description = "대쉬보드의 카테고리별 판매량 을 조회 합니다")
  public List<Map<String, Object>> getCategorySales() {
      return categorySalesService.getCategorySales();
  }
  @GetMapping("/topsell")
  @Operation(summary = "대쉬보드의 상품별 판매량 조회", description = "대쉬보드의 상품별 판매량 을 조회 합니다")
  public List<Map<String, Object>> getTopSellingProducts() {
      return sellService.getTopSellingProducts();
  }

}
