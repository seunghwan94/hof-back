package com.lshwan.hof.service.prod;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.repository.prod.ProdOptionRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProdOptionServiceTests {

    @Autowired
    private ProdOptionService prodOptionService;

    @Autowired
    private ProdOptionRepository prodOptionRepository;

    @Test
    @Transactional
    public void addTest() {
        //  옵션 추가
        ProdOption prodOption = ProdOption.builder()
            .type("색상")
            .value("빨강")
            .addPrice(1000)
            .build();

        ProdOption savedOption = prodOptionService.add(prodOption);

        //  검증
        assertNotNull(savedOption);
        assertNotNull(savedOption.getNo());
        log.info("옵션 추가 완료: {}", savedOption);
    }

    @Test
    public void findByTest() {
        //  존재하는 옵션 ID
        Long optionNo = 3254L;

        //  옵션 조회
        ProdOption prodOption = prodOptionService.findBy(optionNo);

        //  검증
        assertNotNull(prodOption);
        assertNotNull(prodOption.getNo());

        log.info("조회된 옵션: {}", prodOption);
    }

    @Test
    public void findListTest() {
        //  옵션 리스트 조회
        List<ProdOption> options = prodOptionService.findList();

        //  검증
        assertNotNull(options);
        assertTrue(options.size() > 0);

        log.info("옵션 리스트: {}", options);
    }

    @Test
    @Transactional
    public void modifyTest() {
        //  기존 옵션 추가
        ProdOption prodOption = ProdOption.builder()
            .type("사이즈")
            .value("L")
            .addPrice(2000)
            .build();

        ProdOption savedOption = prodOptionService.add(prodOption);
        assertNotNull(savedOption);
        Long optionNo = savedOption.getNo();

        // 옵션 수정
        ProdOption modifiedOption = ProdOption.builder()
            .no(optionNo)
            .type("사이즈")
            .value("XL")
            .addPrice(2500)
            .build();

        Long modifiedNo = prodOptionService.modify(modifiedOption);
        assertNotNull(modifiedNo);

        ProdOption updatedOption = prodOptionService.findBy(modifiedNo);
        assertNotNull(updatedOption);
        assertTrue(updatedOption.getValue().equals("XL"));

        log.info("수정된 옵션: {}", updatedOption);
    }

    @Test
    @Transactional
    public void removeTest() {
        //  기존 옵션 추가
        ProdOption prodOption = ProdOption.builder()
            .type("소재")
            .value("면")
            .addPrice(0)
            .build();

        ProdOption savedOption = prodOptionService.add(prodOption);
        assertNotNull(savedOption);
        Long optionNo = savedOption.getNo();

        //  옵션 삭제
        boolean isRemoved = prodOptionService.remove(optionNo);
        assertTrue(isRemoved);

        log.info("삭제된 옵션 번호: {}", optionNo);
    }
}
