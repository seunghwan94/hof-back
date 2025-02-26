package com.lshwan.hof.service.login;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.member.MemberAddrDto;
import com.lshwan.hof.domain.dto.member.MemberDetailDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberAddr;
import com.lshwan.hof.repository.member.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberDetailServiceImpl implements MemberDetailService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDetailDto getMemberDetail(Long mno) {
      Member member = memberRepository.findWithAddressesByMno(mno)
      .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

      return MemberDetailDto.fromEntity(member);
    }

    @Transactional
    public void updateMember(Long mno, MemberDetailDto updatedDto) {
        Member member = memberRepository.findWithAddressesByMno(mno)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        // ✅ 1. 개인정보 업데이트 (마케팅 동의, 푸시 알림 설정)
        if (member.getMemberDetail() != null) {
            member.getMemberDetail().setMarketingConsent(updatedDto.isMarketingConsent());
            member.getMemberDetail().setAllowNotification(updatedDto.isAllowNotification());
        }

        // ✅ 2. 주소 정보 업데이트
        if (updatedDto.getAddresses() != null && !updatedDto.getAddresses().isEmpty()) {
            // 기존 주소 리스트 가져오기
            List<MemberAddr> existingAddresses = member.getMemberAddrList();

            // 모든 기존 주소의 `default` 값을 false로 변경
            existingAddresses.forEach(addr -> addr.setDefault(false));

            for (MemberAddrDto newAddrDto : updatedDto.getAddresses()) {
                // 기존 주소가 있으면 업데이트, 없으면 추가
                MemberAddr existingAddr = existingAddresses.stream()
                        .filter(addr -> addr.getRoadAddr().equals(newAddrDto.getRoadAddr()))
                        .findFirst()
                        .orElse(null);

                if (existingAddr != null) {
                    existingAddr.setZipcode(newAddrDto.getZipcode());
                    existingAddr.setDetailAddr(newAddrDto.getDetailAddr());
                    existingAddr.setDefault(newAddrDto.isDefault());
                } else {
                    MemberAddr newAddr = MemberAddr.builder()
                            .zipcode(newAddrDto.getZipcode())
                            .roadAddr(newAddrDto.getRoadAddr())
                            .detailAddr(newAddrDto.getDetailAddr())
                            .isDefault(newAddrDto.isDefault())
                            .member(member)
                            .build();
                    existingAddresses.add(newAddr);
                }
            }

            // `default: true`인 주소가 하나도 없으면, 첫 번째 주소를 기본 주소로 설정
            if (existingAddresses.stream().noneMatch(MemberAddr::isDefault) && !existingAddresses.isEmpty()) {
                existingAddresses.get(0).setDefault(true);
            }
        }

        memberRepository.save(member);
    }

}