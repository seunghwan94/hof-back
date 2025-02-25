package com.lshwan.hof.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@Log4j2
public class VisionConfig {

    @Value("${google.vision.credentials.private_key}")
    private String privateKey;

    @Value("${google.vision.credentials.private_key_id}")
    private String privateKeyId;

    @Value("${google.vision.credentials.client_email}")
    private String clientEmail;

    @Value("${google.vision.credentials.client_id}")
    private String clientId;

    @Value("${google.vision.credentials.project_id}")
    private String projectId;

    @Bean
    public ImageAnnotatorClient imageAnnotatorClient() throws IOException {
        // 🔹 private_key의 줄바꿈(\n) 변환
        String formattedPrivateKey = privateKey.replace("\n", "\\n");

        // 🔹 JSON 키 값 생성 (환경 변수 방식 제거)
        String jsonKey = "{"
                + "\"type\": \"service_account\","
                + "\"project_id\": \"" + projectId + "\","
                + "\"private_key_id\": \"" + privateKeyId + "\","
                + "\"private_key\": \"" + formattedPrivateKey + "\","
                + "\"client_email\": \"" + clientEmail + "\","
                + "\"client_id\": \"" + clientId + "\","
                + "\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\","
                + "\"token_uri\": \"https://oauth2.googleapis.com/token\","
                + "\"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\","
                + "\"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/" + clientEmail + "\""
                + "}";

        // 🔹 JSON 문자열을 기반으로 GoogleCredentials 객체 생성
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new java.io.ByteArrayInputStream(jsonKey.getBytes(StandardCharsets.UTF_8))
        ).createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        // 🔹 Google Vision API 클라이언트 생성
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();

        return ImageAnnotatorClient.create(settings);
    }
}
