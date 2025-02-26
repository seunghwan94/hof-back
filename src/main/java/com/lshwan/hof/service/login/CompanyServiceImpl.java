package com.lshwan.hof.service.login;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.domain.dto.member.CompanyDto;
import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.member.Company;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.common.FileMasterRepository;
import com.lshwan.hof.repository.member.CompanyRepository;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.service.S3Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;
    private final FileMasterRepository fileMasterRepository;
    private final S3Service s3Service;  // 이미지 업로드용 S3 서비스 가정

    /**
     * 회사 등록
     */
    @Override
    @Transactional
    public CompanyDto addCompany(CompanyDto companyDto, List<MultipartFile> images) {
        // 회원 조회
        Member member = memberRepository.findById(companyDto.getMemberNo())
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        // Company 엔티티 생성
        Company company = Company.builder()
                .name(companyDto.getName())
                .title(companyDto.getTitle())
                .info(companyDto.getInfo())
                .tag(companyDto.getTag())
                .pointText(companyDto.getPointText())
                .content(companyDto.getContent())
                .tel(companyDto.getTel())
                .count(companyDto.getCount())
                .member(member)
                .build();

        // Company 저장
        companyRepository.save(company);

        // 이미지 업로드 및 URL 저장
        List<String> uploadedImageUrls = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String imageUrl = s3Service.settingFile(image, "company", company);
                uploadedImageUrls.add(imageUrl);
            }
        }

        // DTO 반환
        return CompanyDto.builder()
                .no(company.getNo())
                .name(company.getName())
                .title(company.getTitle())
                .info(company.getInfo())
                .tag(company.getTag())
                .pointText(company.getPointText())
                .content(company.getContent())
                .tel(company.getTel())
                .count(company.getCount())
                .memberNo(member.getMno())
                .imageUrls(uploadedImageUrls)
            .build();
    }

    /**
     * 회사 조회
     */
    @Override
    @Transactional
    public CompanyDto getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        Long memberNo = company.getMember().getMno();

        // FileMaster에서 이미지 조회
        List<FileMaster> images = fileMasterRepository.findByMember_mno(memberNo);
        List<String> imageUrls = images.stream()
                .map(file -> file.getFileUrl()) // getFileUrl 메서드 호출
                .collect(Collectors.toList());

        return CompanyDto.builder()
                .no(company.getNo())
                .name(company.getName())
                .title(company.getTitle())
                .info(company.getInfo())
                .tag(company.getTag())
                .pointText(company.getPointText())
                .content(company.getContent())
                .tel(company.getTel())
                .count(company.getCount())
                .memberNo(memberNo)
                .imageUrls(imageUrls) // 이미지 URL 리스트 추가
            .build();
    }


    /**
     * 회사 목록 조회
     */
    @Override
    @Transactional
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(company -> {
            Long memberNo = company.getMember().getMno();

            // FileMaster에서 이미지 조회
            List<FileMaster> images = fileMasterRepository.findByMember_mno(memberNo);
            List<String> imageUrls = images.stream()
                    .map(FileMaster::getFileUrl) // FileMaster에서 파일 URL을 추출하는 메서드
                    .collect(Collectors.toList());

                return CompanyDto.builder()
                        .no(company.getNo())
                        .name(company.getName())
                        .title(company.getTitle())
                        .info(company.getInfo())
                        .tag(company.getTag())
                        .pointText(company.getPointText())
                        .content(company.getContent())
                        .tel(company.getTel())
                        .count(company.getCount())
                        .memberNo(memberNo)
                        .imageUrls(imageUrls) // 조회한 이미지 URL 리스트 추가
                        .build();
        }).collect(Collectors.toList());
    }
}
