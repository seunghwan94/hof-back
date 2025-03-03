package com.lshwan.hof.service.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.member.CompanyDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.service.S3Service;
import com.lshwan.hof.service.login.CompanyService;
import com.lshwan.hof.service.login.MemberService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CompanyServiceTests {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private S3Service s3Service;

    @Test
    @Transactional
    public void addCompanyTest() {
        // 1️⃣ 테스트용 회원 가져오기
        Member member = memberService.findBy("hof2");
        assertNotNull(member);

        // 2️⃣ 회사 추가
        CompanyDto companyDto = CompanyDto.builder()
            .memberNo(member.getMno())
            .name("테스트 회사")
            .title("테스트 타이틀")
            .info("테스트 정보")
            .tag("테스트 태그")
            .pointText("포인트 설명")
            .content("회사 설명")
            .tel("010-1234-5678")
            .count(5)
            .build();

        Long companyNo = companyService.addCompany(companyDto, null).getNo();
        assertNotNull(companyNo);
        log.info("회사 추가 완료: no = {}", companyNo);
    }
    @Test
    public void getCompanyTest() {
        // 1️⃣ 존재하는 회사 ID
        Long companyNo = 1L;

        // 2️⃣ 회사 조회
        CompanyDto company = companyService.getCompany(companyNo);

        // 3️⃣ 검증
        assertNotNull(company);
        assertNotNull(company.getNo());
        assertNotNull(company.getName());

        log.info("조회된 회사: {}", company);
    }


    @Test
    @Transactional
    public void getAllCompaniesTest() {
        // when
        List<CompanyDto> companies = companyService.getAllCompanies();

        // then
        assertNotNull(companies);
        assertTrue(companies.size() > 0);

        // 로그 출력 (확인용)
        companies.forEach(company -> {
            log.info("Company: {}", company);
        });
    }
}
