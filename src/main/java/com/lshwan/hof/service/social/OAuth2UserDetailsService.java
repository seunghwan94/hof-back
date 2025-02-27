// package com.lshwan.hof.service.social;

// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Service;

// import com.lshwan.hof.domain.entity.member.Member;
// import com.lshwan.hof.domain.entity.member.MemberDetail;
// import com.lshwan.hof.domain.entity.social.Social;
// import com.lshwan.hof.repository.member.MemberDetailRepository;
// import com.lshwan.hof.repository.member.MemberRepository;
// import com.lshwan.hof.repository.social.SocialRepository;
// import com.lshwan.hof.config.JwtTokenProvider;  // JwtTokenProvider 추가

// @Service
// public class OAuth2UserDetailsService {

//     private final MemberRepository memberRepository;
//     private final SocialRepository socialRepository;
//     private final MemberDetailRepository memberDetailRepository;
//     private final JwtTokenProvider jwtTokenProvider;  // JwtTokenProvider 주입

//     public OAuth2UserDetailsService(MemberRepository memberRepository, 
//                                     SocialRepository socialRepository, 
//                                     MemberDetailRepository memberDetailRepository, 
//                                     JwtTokenProvider jwtTokenProvider) {
//         this.memberRepository = memberRepository;
//         this.socialRepository = socialRepository;
//         this.memberDetailRepository = memberDetailRepository;
//         this.jwtTokenProvider = jwtTokenProvider;  // JwtTokenProvider 주입
//     }

//     /**
//      * 소셜 로그인 후 사용자 정보를 저장하거나 업데이트하고 JWT 토큰을 반환
//      * @param oAuth2User - OAuth2User 정보 (구글, 카카오 등)
//      * @return 생성된 JWT 토큰
//      */
//     public String saveOrUpdateSocialUser(OAuth2User oAuth2User) {
//         String email = (String) oAuth2User.getAttributes().get("email");
//         String id = (String) oAuth2User.getAttributes().get("sub");  // OAuth2 ID (sub)

//         // 이미 등록된 소셜 유저인지 확인
//         Social social = socialRepository.findById(id);
//         if (social == null) {
//             Member member = new Member();  // 새로운 Member 생성
//             memberRepository.save(member);  // 회원 저장 (회원 정보는 MemberDetail에서 관리)

//             // MemberDetail에 이메일 설정
//             MemberDetail memberDetail = new MemberDetail();
//             memberDetail.setEmail(email);
//             memberDetail.setMember(member);  // Member와 연결
//             memberDetailRepository.save(memberDetail);  // MemberDetail 저장

//             social = new Social();
//             social.setEmail(email);
//             social.setMember(member);
//             social.setId(id);  // OAuth2 ID 저장
//             socialRepository.save(social);  // 소셜 계정 저장
//         }

//         // JWT 토큰 생성 (소셜 로그인 후)
//         return jwtTokenProvider.generateToken(email);  // 이메일을 기준으로 JWT 토큰 생성
//     }
// }
