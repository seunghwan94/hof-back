package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.service.S3Service;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("file")
@Log4j2
public class FileController {
    @Autowired
    private S3Service s3Service;

    @SuppressWarnings("null")
    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam("file") List<MultipartFile> files) {
         List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            // // S3 업로드
            // s3Service.settingFile(file,"uploads");

            String url = s3Service.settingFile(file, "uploads");
            urls.add(url);
            
        }
        return ResponseEntity.ok().body(urls);
    }
}
