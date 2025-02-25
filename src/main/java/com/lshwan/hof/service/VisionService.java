package com.lshwan.hof.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class VisionService {

    private final ImageAnnotatorClient imageAnnotatorClient;

    public Map<String, Object> detectUnsafeContentFromImageUrl(String imageUrl) {
        Map<String, Object> result = new HashMap<>();
        result.put("imageUrl", imageUrl);

        try {
            // 1. 이미지 데이터를 URL에서 읽어오기
            URL url = new URL(imageUrl);
            ByteString imgBytes;
            try (InputStream in = url.openStream()) {
                imgBytes = ByteString.readFrom(in);
            }

            // 2. Google Vision API용 이미지 및 요청 설정
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();

            List<AnnotateImageRequest> requests = List.of(request);

            // 3. Vision API 호출 (싱글턴 클라이언트 사용)
            BatchAnnotateImagesResponse response = imageAnnotatorClient.batchAnnotateImages(requests);

            // 4. 결과 처리
            for (AnnotateImageResponse res : response.getResponsesList()) {
                if (res.hasError()) {
                    result.put("error", res.getError().getMessage());
                    return result;
                }

                SafeSearchAnnotation annotation = res.getSafeSearchAnnotation();
                result.put("adult", annotation.getAdult().name());
                result.put("medical", annotation.getMedical().name());
                result.put("violence", annotation.getViolence().name());
                result.put("racy", annotation.getRacy().name());

                return result;
            }
        } catch (IOException e) {
            log.error("❌ Vision API 오류: {}", e.getMessage(), e);
            result.put("error", "이미지 처리 중 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            log.error("❌ 알 수 없는 오류 발생: {}", e.getMessage(), e);
            result.put("error", "알 수 없는 오류 발생");
        }

        return result;
    }
}
