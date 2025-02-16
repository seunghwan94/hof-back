package com.lshwan.hof.domain.dto.prod;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdDetailDto {
    private Long pno;
    private String title;
    private String content;
    private int price;
    private int stock;
    private List<String> imageUrls; 
    private List<ProdOptionDto> options; 
    

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProdOptionDto {
        private Long optionNo;
        private String type;
        private String value;
        private int addPrice;
        private int stock;
    }
}
