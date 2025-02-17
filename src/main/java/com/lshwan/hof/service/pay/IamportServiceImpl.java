package com.lshwan.hof.service.pay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

@Service
public class IamportServiceImpl implements IamportService{
  @Value("${iamport.api-key}")
  private String apiKey;

  @Value("${iamport.api-secret}")
  private String apiSecret;
  
  @Override
  public String getAccessToken() {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.iamport.kr/users/getToken";

    Map<String, String> body = new HashMap<>();
    body.put("imp_key", apiKey);
    body.put("imp_secret", apiSecret);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
    ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

    return (String) ((Map) response.getBody().get("response")).get("access_token");
  }

  @Override
  public Map<String, Object> validatePayment(String impUid) {
    String token = getAccessToken();
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.iamport.kr/payments/" + impUid;

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

    return (Map<String, Object>) response.getBody().get("response");
  }
  
}
