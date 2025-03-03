package com.lshwan.hof.service.admin;

import com.lshwan.hof.mapper.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategorySalesService {

    private final CategorySalesMapper categorySalesMapper;

    public List<Map<String, Object>> getCategorySales() {
        return categorySalesMapper.getCategorySales();
    }
}
