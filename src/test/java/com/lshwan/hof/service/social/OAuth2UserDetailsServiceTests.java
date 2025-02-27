// package com.lshwan.hof.service.social;

// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.lshwan.hof.domain.dto.auth.AuthRequestDto;
// import com.lshwan.hof.repository.member.MemberDetailRepository;
// import com.lshwan.hof.repository.member.MemberRepository;
// import com.lshwan.hof.repository.social.SocialRepository;

// import lombok.extern.log4j.Log4j2;

// @SpringBootTest
// @Log4j2
// public class OAuth2UserDetailsServiceTests {
//     @Autowired
//     private SocialRepository socialRepository;
//     @Autowired
//     private MemberRepository memberRepository;
//     @Autowired
//     private MemberDetailRepository memberDetailRepository;

//     @Test
//     public void testUserSavedAfterOAuth2Login() throws Exception {
//         // OAuth2 로그인 후 DB에 사용자 정보가 저장되었는지 확인
//         mockMvc.perform(get("/some-protected-endpoint")
//                 .header("Authorization", "Bearer " + accessToken))
//                 .andExpect(status().isOk());

//         // Member, Social, MemberDetail 테이블 확인
//         String oauthId = "test-oauth-id";  // OAuth2 로그인 시 해당 ID를 사용
//         assertNotNull(socialRepository.findById(oauthId));
//         assertNotNull(memberRepository.findByLoginId("testuser"));
//         assertNotNull(memberDetailRepository.findByEmail("testemail@example.com"));
//     }
// }
