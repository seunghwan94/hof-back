package com.lshwan.hof.controller.common;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.domain.dto.member.CompanyDto;
import com.lshwan.hof.service.login.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("common/company")
public class CompanyController {
   private final CompanyService companyService;

    /**
     * 회사 등록 (이미지 업로드 포함)
     * POST /api/v1/companies
     */
    @PostMapping(consumes = {"multipart/form-data"})
      @Operation(summary = "인테리어 등록 ", description = "인테리어 등록 호출 API 입니다")
    public ResponseEntity<CompanyDto> addCompany(
            @Valid @RequestPart("company") CompanyDto companyDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        CompanyDto createdCompany = companyService.addCompany(companyDto, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    /**
     * 회사 상세 조회
     * GET /api/v1/companies/{companyId}
     */
    @GetMapping("/{companyId}")
    @Operation(summary = "인테리어 상세정보 ", description = "인테리어 API의 companyID 값에 따른 상세정보 호출입니다")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable(name = "companyId") Long companyId) {
        CompanyDto companyDto = companyService.getCompany(companyId);
        return ResponseEntity.ok(companyDto);
    }

    /**
     * 회사 목록 조회
     * GET /api/v1/companies
     */
    @GetMapping
    @Operation(summary = "인테리어 리스트", description = "등록된 모든 인테리어를 리스트 데이터값을 반환합니다")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
}
