package com.lshwan.hof.service.login;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.domain.dto.member.CompanyDto;
import com.lshwan.hof.domain.entity.member.Company;
import com.lshwan.hof.domain.entity.member.Member;
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
                .info(companyDto.getInfo())
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
                .info(company.getInfo())
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

        return CompanyDto.builder()
                .no(company.getNo())
                .name(company.getName())
                .info(company.getInfo())
                .content(company.getContent())
                .tel(company.getTel())
                .count(company.getCount())
                .memberNo(company.getMember().getMno())
                .imageUrls(new ArrayList<>()) // 이미지 URL을 불러오는 로직 추가 가능
                .build();
    }

    /**
     * 회사 목록 조회
     */
    @Override
    @Transactional
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(company -> CompanyDto.builder()
                .no(company.getNo())
                .name(company.getName())
                .info(company.getInfo())
                .content(company.getContent())
                .tel(company.getTel())
                .count(company.getCount())
                .memberNo(company.getMember().getMno())
                .imageUrls(new ArrayList<>()) // 이미지 URL을 불러오는 로직 추가 가능
              .build()).collect(Collectors.toList());
    }
}
