package com.lshwan.hof.service.social;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.Member.MemberRole;
import com.lshwan.hof.domain.entity.member.MemberDetail;
import com.lshwan.hof.domain.entity.social.Social;
import com.lshwan.hof.domain.entity.social.Social.SocialType;
import com.lshwan.hof.repository.member.MemberDetailRepository;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.social.SocialRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final SocialRepository socialRepository; // SocialRepository 추가

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 구글 사용자 정보 가져오기
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        log.info("구글에서 받은 이메일: " + email);
        log.info("구글에서 받은 이름: " + name);

        // 이메일을 기준으로 MemberDetail을 조회하고, 없으면 새로 생성
        log.info("이메일로 MemberDetail 조회 시도: " + email);
        Optional<MemberDetail> existingMemberDetail = memberDetailRepository.findByEmail(email);

        Member member;
        if (existingMemberDetail.isPresent()) {
            // 이미 등록된 회원 정보 조회
            member = existingMemberDetail.get().getMember();
            log.info("이미 존재하는 Member: " + member);
        } else {
            // 새로운 MemberDetail과 Member 생성
            log.info("새로운 Member 및 MemberDetail 생성 시도: " + email);

            MemberDetail newMemberDetail = new MemberDetail();
            newMemberDetail.setEmail(email);
            newMemberDetail.setPrivacyConsent(true); // 동의 여부는 설정에 맞게 처리
            newMemberDetail.setMarketingConsent(true);
            newMemberDetail.setAllowNotification(true);

            // 새로운 Member 생성하여 MemberDetail에 연결
            Member newMember = Member.builder()
                    .name(name)
                    .id(email) // 구글 이메일로 ID 설정
                    .pw("google_login") // 구글 로그인 시에는 비밀번호를 사용할 필요가 없으므로 임시값
                    .role(Member.MemberRole.user) // 기본 역할
                    .memberDetail(newMemberDetail) // MemberDetail 연결
                    .build();

            // Member와 MemberDetail 저장
            log.info("새로운 Member 및 MemberDetail 저장 시도: " + newMember);
            memberRepository.save(newMember);

            // Member 객체로 갱신
            member = newMember;
        }

        // Social 테이블에 구글 소셜 로그인 정보 저장
        Social social = Social.builder()
                .id(email) // 구글 이메일을 ID로 사용
                .email(email)
                .name(name)
                .type(Social.SocialType.GOOGLE) // 소셜 타입 설정
                .member(member) // 해당 Member와 연결
                .build();

        // Social 객체 저장
        socialRepository.save(social);
        log.info("Social 테이블에 구글 로그인 정보 저장 완료");

        // 권한 설정 (기본적으로 ROLE_USER)
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        // OAuth2UserAuthority를 사용해 구글 사용자 정보와 권한을 반환
        return new DefaultOAuth2User(authorities, attributes, "name");
    }
}


