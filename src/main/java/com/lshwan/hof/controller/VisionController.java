package com.lshwan.hof.controller;

import com.lshwan.hof.service.VisionService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("file/vision")
@Log4j2
public class VisionController {

    @Autowired
    private VisionService visionService;

    // ✅ POST 요청을 통해 JSON으로 이미지 URL 받기
    @PostMapping("/detect")
    public Map<String, Object> detectUnsafeContent(@RequestBody ImageRequest request) {
        try {
            log.info(request.getImageUrl());
            return visionService.detectUnsafeContentFromImageUrl(request.getImageUrl());
        } catch (Exception e) {
            return Map.of("error", "안전하지 않은 이미지: " + e.getMessage());
        }
    }

    // 내부 DTO 클래스 (JSON 매핑용)
    public static class ImageRequest {
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
