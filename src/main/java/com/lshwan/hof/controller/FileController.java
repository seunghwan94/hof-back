package com.lshwan.hof.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.service.S3Service;

import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("file")
@Log4j2
public class FileController {
    @Autowired
    private S3Service s3Service;
    
    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    
    @Value("${aws.s3.region}")
    private String region;

    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam("file") List<MultipartFile> files) {
        for (MultipartFile file : files) {
            try {
                String origin = file.getOriginalFilename();
                String path = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                String uuid = UUID.randomUUID().toString();
                String ext = "";
                int idx = origin.lastIndexOf(".");
                if (idx > 0) {
                    ext = origin.substring(idx + 1);
                }
                String fileName = uuid + "." + ext;
                String key = "uploads/" + path + "/" + fileName;
                String mimeType = file.getContentType();
                byte[] content = file.getBytes();
                
                // S3 업로드
                s3Service.uploadFile(key, content, mimeType);
                
                String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
                log.info("File uploaded successfully: {}", fileUrl);
            } catch (Exception e) {
                log.error("File upload failed: {}", e.getMessage());
            }
        }
        
        return ResponseEntity.ok().body("File(s) uploaded successfully");
    }
}
