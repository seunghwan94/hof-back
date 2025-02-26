package com.lshwan.hof.domain.dto.member;

import java.util.List;
import java.util.stream.Collectors;

import com.lshwan.hof.domain.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDetailDto {
    
    private String id;
    private String name;
    private String role;

    private String email;
    private String gender;
    private boolean privacyConsent;
    private boolean marketingConsent;
    private boolean allowNotification;
    private boolean emailVerification;

    private List<MemberAddrDto> addresses; // 여러 개의 주소 저장

    // Member 엔티티에서 DTO 변환하는 메서드
    public static MemberDetailDto fromEntity(Member member) {
        if (member == null) {
            return null;
        }

        return MemberDetailDto.builder()
                .id(member.getId())
                .name(member.getName())
                .role(member.getRole().name())
                .email(member.getMemberDetail() != null ? member.getMemberDetail().getEmail() : null)
                .gender(member.getMemberDetail() != null ? 
                        (member.getMemberDetail().getGender() != null ? 
                        member.getMemberDetail().getGender().name() : null) : null)
                .privacyConsent(member.getMemberDetail() != null && member.getMemberDetail().isPrivacyConsent())
                .marketingConsent(member.getMemberDetail() != null && member.getMemberDetail().isMarketingConsent())
                .allowNotification(member.getMemberDetail() != null && member.getMemberDetail().isAllowNotification())
                .emailVerification(member.getMemberDetail() != null &&
                        member.getMemberDetail().getEmailVerification() != null)
                .addresses(member.getMemberAddrList() != null ?
                    member.getMemberAddrList().stream()
                            .map(MemberAddrDto::fromEntity)
                            .collect(Collectors.toList()) : null)
            .build();
    }
}
