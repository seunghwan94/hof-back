package com.lshwan.hof.service.pay;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.*;

@Service
@Log4j2
public class IamportServiceImpl implements IamportService{
  @Value("${iamport.api-key}")
  private String apiKey;

  @Value("${iamport.api-secret}")
  private String apiSecret;
  
  @Override
  public String getAccessToken() {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.iamport.kr/users/getToken";

    ObjectMapper objectMapper = new ObjectMapper();
    String requestBody = null;

    try {
      Map<String, String> body = new HashMap<>();
      body.put("imp_key", apiKey);
      body.put("imp_secret", apiSecret);
      requestBody = objectMapper.writeValueAsString(body); // JSON 변환
    } catch (JsonProcessingException e) {
      log.error("JSON 변환 실패", e);
      throw new RuntimeException("JSON 변환 실패");
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    
    // log.info("IAMPORT API Key: {}", apiKey);
    // log.info("IAMPORT API Secret: {}", apiSecret != null ? "*****" : "NULL");
    // log.info("IAMPORT 요청 JSON: {}", requestBody);

    ResponseEntity<Map> response;
    try {
      response = restTemplate.postForEntity(url, entity, Map.class);
      // log.info("IAMPORT 응답: {}", response.getBody());
    } catch (Exception e) {
      log.error("IAMPORT 토큰 요청 실패", e);
      throw new RuntimeException("IAMPORT 토큰 요청 실패", e);
    }

    if (response.getBody() == null || response.getBody().get("response") == null) {
      throw new RuntimeException("IAMPORT 토큰 응답이 null입니다.");
    }

    return (String) ((Map) response.getBody().get("response")).get("access_token");
  }


  @Override
  public Map<String, Object> validatePayment(String impUid) {
    // log.info("IAMPORT Access Token 요청");
    String token = getAccessToken();  // IAMPORT 토큰 가져오기
    // log.info("IAMPORT Access Token: {}", token);
    
    if (token == null || token.isEmpty()) {
        throw new RuntimeException("IAMPORT Access Token이 null 또는 비어 있습니다.");
    }

    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.iamport.kr/payments/" + impUid;

    // log.info("IAMPORT 결제 검증 요청 impUid: {}", impUid);

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    
    try {
      ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
      // log.info("IAMPORT 결제 검증 응답: {}", response.getBody());
      if (response.getBody() == null || response.getBody().get("response") == null) {
          throw new RuntimeException("IAMPORT 결제 검증 응답이 null입니다.");
      }

      return (Map<String, Object>) response.getBody().get("response");

    } catch (Exception e) {
      log.error("IAMPORT 결제 검증 실패", e);
      throw new RuntimeException("IAMPORT 결제 검증 실패", e);
    }
  }

  
}
