package com.lshwan.hof.service.prod;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.repository.prod.ProdRepository;
import com.lshwan.hof.repository.prod.ProdCategoryRepository;
import com.lshwan.hof.repository.prod.ProdOptionRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProdOptionMapServiceTests {

    @Autowired
    private ProdOptionMapService prodOptionMapService;

    @Autowired
    private ProdRepository prodRepository;
     @Autowired
    private ProdCategoryRepository prodCategoryRepository;

    @Autowired
    private ProdOptionRepository prodOptionRepository;

    @Test
    @Transactional
    public void addTest() {
      ProdCategory category = ProdCategory.builder()
      .cno(1L)
      .build();
        //  테스트용 상품 추가
        Prod prod = Prod.builder()
            .title("테스트 상품")
            .content("설명")
            .price(10000)
            .stock(50)
            .cno(category)
            .build();
        prodRepository.save(prod);
        assertNotNull(prod.getPno());

        //  테스트용 옵션 추가
        ProdOption option = ProdOption.builder()
            .type("색상")
            .value("빨강")
            .addPrice(1000)
            .build();
        prodOptionRepository.save(option);
        assertNotNull(option.getNo());


        ProdOptionMap optionMap = ProdOptionMap.builder()
            .prod(prod)
            .option(option)
            .stock(10)
            .build();

        Long optionMapNo = prodOptionMapService.add(optionMap);
        assertNotNull(optionMapNo);
        log.info("옵션 매핑 추가 완료: {}", optionMapNo);
    }

    @Test
    public void findByTest() {
        //  존재하는 옵션 매핑 ID
        Long optionMapNo = 885L;

        //  옵션 매핑 조회
        ProdOptionMap optionMap = prodOptionMapService.findBy(optionMapNo);


        assertNotNull(optionMap);
        assertNotNull(optionMap.getNo());

        log.info("조회된 옵션 매핑: {}", optionMap);
    }

    @Test
    public void findListTest() {
        //  옵션 매핑 리스트 조회
        List<ProdOptionMap> optionMaps = prodOptionMapService.findList();


        assertNotNull(optionMaps);
        assertTrue(optionMaps.size() > 0);

        log.info("옵션 매핑 리스트: {}", optionMaps);
    }

    @Test
    @Transactional
    public void modifyTest() {
      ProdCategory category = ProdCategory.builder()
      .cno(1L)
      .build();
        //  테스트용 상품 추가
        Prod prod = Prod.builder()
            .title("수정 테스트 상품")
            .content("설명")
            .price(20000)
            .stock(100)
            .cno(category)
            .build();
        prodRepository.save(prod);
        assertNotNull(prod.getPno());

        //  테스트용 옵션 추가
        ProdOption option = ProdOption.builder()
            .type("사이즈")
            .value("M")
            .addPrice(2000)
            .build();
        prodOptionRepository.save(option);
        assertNotNull(option.getNo());

        //  옵션 매핑 추가
        ProdOptionMap optionMap = ProdOptionMap.builder()
            .prod(prod)
            .option(option)
            .stock(20)
            .build();

        Long optionMapNo = prodOptionMapService.add(optionMap);
        assertNotNull(optionMapNo);

        // 옵션 매핑 수정
        ProdOptionMap modifiedOptionMap = ProdOptionMap.builder()
            .no(optionMapNo)
            .prod(prod)
            .option(option)
            .stock(30)
            .build();

        Long modifiedNo = prodOptionMapService.modify(modifiedOptionMap);
        assertNotNull(modifiedNo);

        ProdOptionMap updatedOptionMap = prodOptionMapService.findBy(modifiedNo);
        assertNotNull(updatedOptionMap);
        assertTrue(updatedOptionMap.getStock() == 30);

        log.info("수정된 옵션 매핑: {}", updatedOptionMap);
    }

    @Test
    @Transactional
    public void removeTest() {
      ProdCategory category = ProdCategory.builder()
            .cno(1L)
            .build();
        prodCategoryRepository.save(category);
        assertNotNull(category.getCno());
        //  테스트용 상품 추가
        Prod prod = Prod.builder()
            .title("삭제 테스트 상품")
            .content("설명")
            .price(30000)
            .stock(200)
            .cno(category)
            .build();
        prodRepository.save(prod);
        assertNotNull(prod.getPno());

        // 테스트용 옵션 추가
        ProdOption option = ProdOption.builder()
            .type("소재")
            .value("면")
            .addPrice(500)
            .build();
        prodOptionRepository.save(option);
        assertNotNull(option.getNo());

        //  옵션 매핑 추가
        ProdOptionMap optionMap = ProdOptionMap.builder()
            .prod(prod)
            .option(option)
            .stock(15)
            .build();

        Long optionMapNo = prodOptionMapService.add(optionMap);
        assertNotNull(optionMapNo);

        // 옵션 매핑 삭제
        boolean isRemoved = prodOptionMapService.remove(optionMapNo);
        assertTrue(isRemoved);

        log.info("삭제된 옵션 매핑 번호: {}", optionMapNo);
    }
}
