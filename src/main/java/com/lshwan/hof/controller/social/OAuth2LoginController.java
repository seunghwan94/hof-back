// package com.lshwan.hof.controller.social;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.lshwan.hof.domain.dto.social.GoogleLoginRequest;
// import com.lshwan.hof.service.social.OAuth2LoginService;

// import lombok.AllArgsConstructor;
// import lombok.extern.log4j.Log4j2;

// @RestController
// @RequestMapping("/")
// @AllArgsConstructor
// @Log4j2
// public class OAuth2LoginController {

//     @Autowired
//     private OAuth2LoginService oAuth2LoginService;  // 서비스가 있다면 사용

//     @PostMapping("/login/oauth2/code/google")
//     public ResponseEntity<String> googleLogin(@RequestBody GoogleLoginRequest request) {
//         // 받은 토큰을 처리하는 서비스 호출
//         String token = request.getGoogleToken();
        
//         if (token != null) {
//             oAuth2LoginService.processGoogleLogin(token);  // 예시: 서비스에서 토큰 처리
//             return ResponseEntity.ok()
//                     .header("Cross-Origin-Opener-Policy", "same-origin")  // COOP 헤더 추가
//                     .body("Google login successful");
//         } else {
//             log.info("Google login failed");
//         }
        
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .header("Cross-Origin-Opener-Policy", "same-origin")  // COOP 헤더 추가
//                 .body("Invalid token");
//     }
// }

