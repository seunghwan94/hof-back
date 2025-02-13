package com.lshwan.hof.service;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.imageio.ImageIO;

@Service
@Log4j2
public class S3Service {

  @Value("${aws.s3.bucket-name}")
  private String bucketName;

  @Value("${aws.s3.region}")
  private String region;

  private final S3Client s3Client;

  public S3Service(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public void settingFile(MultipartFile file, String folder) {
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
      String key = folder + "/" + path + "/" + fileName;
      String mimeType = file.getContentType();

      byte[] content = file.getBytes();
      uploadFile(key, content, mimeType);
      String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
      log.info("원본 파일 업로드 완료: {}", fileUrl);

      // **썸네일 생성 및 업로드 (JPG, PNG만 가능)**
      if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png")) {
        byte[] thumbnailContent = createThumbnail(content, ext);
        String thumbnailKey = "uploads/" + path + "/thumb_" + fileName;
        uploadFile(thumbnailKey, thumbnailContent, mimeType);
        String thumbnailUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, thumbnailKey);
        log.info("썸네일 업로드 완료: {}", thumbnailUrl);
      }

    } catch (IOException e) {
      log.error("파일 업로드 실패: {}", e.getMessage());
    }
  }

  public String uploadFile(String key, byte[] content, String mimeType) {
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .contentType(mimeType)
      .build();

    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(content));

    return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
  }

  /**
   * 이미지 썸네일 생성 (최대 200x200)
   */
  private byte[] createThumbnail(byte[] imageBytes, String format) throws IOException {
    try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
         ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

      BufferedImage originalImage = ImageIO.read(bais);

      Thumbnails.of(originalImage)
        .size(200, 200)
        .outputFormat(format)
        .toOutputStream(baos);

      return baos.toByteArray();
    }
  }
}
