package com.lshwan.hof.service.login;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.domain.dto.member.CompanyDto;

public interface CompanyService {

  // 회사 등록 메서드
  CompanyDto addCompany(CompanyDto companyDto, List<MultipartFile> images);

  // 회사 조회 메서드
  CompanyDto getCompany(Long companyId);

  // 회사 리스트 조회 (옵션)
  List<CompanyDto> getAllCompanies();
}