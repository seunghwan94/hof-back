package com.lshwan.hof.domain.dto.member;
import com.lshwan.hof.domain.entity.member.MemberAddr;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddrDto {
    
    private String zipcode;
    private String roadAddr;
    private String detailAddr;
    private boolean isDefault;

    // MemberAddr 엔티티에서 DTO 변환
    public static MemberAddrDto fromEntity(MemberAddr memberAddr) {
        if (memberAddr == null) {
            return null;
        }

        return MemberAddrDto.builder()
                .zipcode(memberAddr.getZipcode())
                .roadAddr(memberAddr.getRoadAddr())
                .detailAddr(memberAddr.getDetailAddr())
                .isDefault(memberAddr.isDefault())
                .build();
    }
}
