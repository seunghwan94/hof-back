package com.lshwan.hof.domain.dto.prod;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdDetailDto {
    private Long pno;
    private String title;
    private String content;
    private int price;
    private int stock;
    private Long cno;
    private List<String> imageUrls; 
    private List<ProdOptionDto> options; 
    private List<String> thumbnailUrl; 

    @Getter
    @Setter
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
