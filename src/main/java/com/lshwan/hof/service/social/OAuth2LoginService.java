// package com.lshwan.hof.service.social;

// import java.util.Map;

// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// @Service
// public class OAuth2LoginService {
//       private static final String GOOGLE_API_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";

//     public void processGoogleLogin(String googleToken) {
//         // 구글 OAuth2 토큰 검증
//         String url = GOOGLE_API_URL + googleToken;
        
//         RestTemplate restTemplate = new RestTemplate();
//         ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
//         if (response.getStatusCode().is2xxSuccessful()) {
//             // 구글에서 사용자 정보 받아오기 성공
//             Map<String, Object> userInfo = response.getBody();
//             System.out.println("User info from Google: " + userInfo);
//         } else {
//             // 오류 처리
//             System.out.println("Google token validation failed");
//         }
//     }
// }
