// package com.lshwan.hof.service.social;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
// import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
// import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
// import org.springframework.stereotype.Service;

// import com.lshwan.hof.domain.entity.member.Member;
// import com.lshwan.hof.domain.entity.member.Member.MemberRole;
// import com.lshwan.hof.domain.entity.member.MemberDetail;
// import com.lshwan.hof.domain.entity.social.Social;
// import com.lshwan.hof.domain.entity.social.Social.SocialType;
// import com.lshwan.hof.repository.member.MemberDetailRepository;
// import com.lshwan.hof.repository.member.MemberRepository;
// import com.lshwan.hof.repository.social.SocialRepository;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.log4j.Log4j2;

// import java.util.Collection;
// import java.util.Collections;
// import java.util.Map;
// import java.util.Optional;

// @Service
// @RequiredArgsConstructor
// @Log4j2
// public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

//     private final MemberRepository memberRepository;
//     private final MemberDetailRepository memberDetailRepository;

//     @Override
//     public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//         OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        
//         // 구글 사용자 정보 가져오기
//         Map<String, Object> attributes = oauth2User.getAttributes();
//         String email = (String) attributes.get("email");
//         String name = (String) attributes.get("name");
//         log.info("구글에서 받은 이메일: " + email);
//         log.info("구글에서 받은 이름: " + name);

//         // 이메일을 기준으로 MemberDetail을 조회하고, 없으면 새로 생성
//         log.info("이메일로 MemberDetail 조회 시도: " + email);
//         Optional<MemberDetail> existingMemberDetail = memberDetailRepository.findByEmail(email);
//         if (existingMemberDetail.isPresent()) {
//             log.info("이미 존재하는 MemberDetail: " + existingMemberDetail.get());
//         } else {
//             log.info("새로운 MemberDetail 생성 시도: " + email);
//             // 새로운 MemberDetail과 Member 생성
//             MemberDetail newMemberDetail = new MemberDetail();
//             newMemberDetail.setEmail(email);
//             newMemberDetail.getMember().setName(name);

//             // 새로운 Member 생성하여 MemberDetail에 연결
//             Member newMember = new Member();
//             newMember.setName(name);
//             newMember.setMemberDetail(newMemberDetail);

//             // Member와 MemberDetail을 저장
//             log.info("새로운 Member 및 MemberDetail 저장 시도: " + newMember + ", " + newMemberDetail);
//             memberRepository.save(newMember);
//             log.info("새로운 Member 및 MemberDetail 저장 완료");
//         }

//         // 이미 등록된 Member 찾기
//         log.info("등록된 회원 조회 시도: " + email);
//         Member member = memberRepository.findById(existingMemberDetail.get().getMember().getMno())
//                 .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
//         log.info("등록된 회원 정보: " + member);

//         // 권한 설정 (기본적으로 ROLE_USER)
//         log.info("권한 설정: ROLE_USER");
//         Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
//             new SimpleGrantedAuthority("ROLE_USER")
//         );

//         // OAuth2UserAuthority를 사용해 구글 사용자 정보와 권한을 반환
//         log.info("최종 반환할 OAuth2User 정보: " + oauth2User);
//         return new DefaultOAuth2User(authorities, attributes, "name");
//     }
// }


