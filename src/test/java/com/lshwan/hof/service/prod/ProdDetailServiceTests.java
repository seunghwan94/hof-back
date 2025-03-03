package com.lshwan.hof.service.prod;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.prod.ProdDetailDto;
import com.lshwan.hof.domain.dto.prod.ProdDetailDto.ProdOptionDto;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ProdDetailServiceTests {

  @Autowired
  private ProdDetailService service;
  @Autowired
  private EntityManager manager;
  @Test
  public void findByTest() {
    // given
    Long pno = 406L;
    // when
    ProdDetailDto foundProduct = service.findBy(pno);
    // then
    assertNotNull(foundProduct);
  }
      @Test
    @Transactional
    public void addTest() {
        // given
        ProdDetailDto productDto = ProdDetailDto.builder()
            .title("테스트 상품")
            .content("테스트 설명")
            .price(10000)
            .stock(50)
            .cno(1L) // 가상의 카테고리 번호
            .options(Arrays.asList(
                ProdOptionDto.builder().type("색상").value("빨강").addPrice(1000).stock(10).build(),
                ProdOptionDto.builder().type("색상").value("파랑").addPrice(2000).stock(5).build()
            ))
            .thumbnailUrl(Arrays.asList("https://example.com/image1.jpg"))
            .imageUrls(Arrays.asList("https://example.com/image2.jpg", "https://example.com/image3.jpg"))
            .build();

        // when
        Long pno = service.add(productDto);

        // then
        assertNotNull(pno);
        assertTrue(pno > 0);

        log.info("상품 등록 완료: pno = {}", pno);
    }

    

    @Test
    @Transactional
    public void modifyTest() {
        // given
        ProdDetailDto productDto = ProdDetailDto.builder()
            .pno(1L) // 존재하는 상품 번호
            .title("수정된 상품명")
            .content("수정된 설명")
            .price(15000)
            .stock(30)
            .options(Arrays.asList(
                ProdOptionDto.builder().optionNo(1L).type("색상").value("초록").addPrice(3000).stock(15).build()
            ))
            .imageUrls(Arrays.asList("https://example.com/modified1.jpg"))
            .build();

        // when
        Long modifiedPno = service.modify(productDto);

        // then
        assertNotNull(modifiedPno);
        assertTrue(modifiedPno > 0);

        log.info("수정된 상품: pno = {}", modifiedPno);
    }

    @Test
    @Transactional
    public void removeTest() {
        // given
        Long pno = 2L; // 존재하는 상품 번호

        // when
        boolean isRemoved = service.remove(pno);

        // then
        assertTrue(isRemoved);

        log.info("삭제된 상품: pno = {}", pno);
    }

    @Test
    @Transactional
    public void removeOptionTest() {

        // 1️ 옵션이 포함된 상품 추가
        ProdDetailDto productDto = ProdDetailDto.builder()
            .title("테스트 상품")
            .content("테스트 설명")
            .price(10000)
            .stock(50)
            .cno(1L) // 가상의 카테고리 번호
            .options(Arrays.asList(
                ProdOptionDto.builder().type("색상").value("빨강").addPrice(1000).stock(10).build()
            ))
            .build();
    
        Long pno = service.add(productDto);
        assertNotNull(pno);
        log.info("상품 추가 완료, pno = {}", pno);
    
        //  강제 flush() 호출하여 DB 반영
        manager.flush();
        manager.clear();
    
        // 2️ 해당 상품의 옵션을 가져옴
        ProdDetailDto savedProduct = service.findBy(pno);
        assertNotNull(savedProduct);
        assertTrue(savedProduct.getOptions().size() > 0);
        
        Long ono = savedProduct.getOptions().get(0).getOptionNo(); // 첫 번째 옵션 번호
        assertNotNull(ono);
        log.info("추가된 옵션 번호: ono = {}", ono);
    
        //  옵션이 상품과 매핑되었는지 확인
        assertNotNull(service.findBy(pno).getOptions().get(0).getOptionNo());
        log.info("옵션 매핑 확인 완료: ono = {}", ono);
    
        //  옵션이 `ProdOptionMap`과 연결되었는지 체크
        assertTrue(service.findBy(pno).getOptions().get(0).getStock() >= 0);
        log.info("옵션과 `ProdOptionMap`이 정상적으로 연결됨");
    
        // 3️ 옵션 삭제
        boolean isRemoved = service.removeOption(ono);
    
        // 4️ 삭제 검증
        assertTrue(isRemoved);
        log.info("삭제된 옵션: ono = {}", ono);
    }
    


}
