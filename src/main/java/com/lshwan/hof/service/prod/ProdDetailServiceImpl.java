package com.lshwan.hof.service.prod;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lshwan.hof.domain.dto.prod.ProdDetailDto;
import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.repository.common.FileMasterRepository;
import com.lshwan.hof.repository.prod.ProdOptionMapRepository;
import com.lshwan.hof.repository.prod.ProdOptionRepository;
import com.lshwan.hof.repository.prod.ProdRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdDetailServiceImpl implements ProdDetailService {
    
  private final ProdRepository prodRepository;
  private final ProdOptionRepository prodOptionRepository;

  private final FileMasterRepository fileMasterRepository;

      @Autowired
      private ProdOptionMapRepository optionMapRepository;
      
    @Override
    @Transactional
    public Long add(ProdDetailDto productDto) {
        // 상품 정보 저장
        Prod prod = Prod.builder()
            .title(productDto.getTitle())
            .content(productDto.getContent())
            .price(productDto.getPrice())
            .stock(productDto.getStock())
            .cno(ProdCategory.builder().cno(productDto.getCno()).build())
            .build();

        prodRepository.save(prod);

        // 옵션 정보 저장 (상품과 연결)
        if (productDto.getOptions() != null && !productDto.getOptions().isEmpty()) {
            for (ProdDetailDto.ProdOptionDto optionDto : productDto.getOptions()) {
                ProdOption newOption = ProdOption.builder()
                    .type(optionDto.getType())
                    .value(optionDto.getValue())
                    .addPrice(optionDto.getAddPrice())
                    .build();

                prodOptionRepository.save(newOption);

                // 브릿지 테이블로 상품과 옵션 연결
                ProdOptionMap optionMap = ProdOptionMap.builder()
                    .prod(prod)  // 상품과 연결
                    .option(newOption) // 옵션과 연결
                    .stock(optionDto.getStock()) // 재고 설정
                    .build();

                optionMapRepository.save(optionMap);
            }
        }
        
        if (productDto.getThumbnailUrl() != null && !productDto.getThumbnailUrl().isEmpty()) {
          List<FileMaster> thumbnailFiles = fileMasterRepository.findByUrlIn(productDto.getThumbnailUrl());
          for (FileMaster file : thumbnailFiles) {
              file.setProd(prod);
          }
          fileMasterRepository.saveAll(thumbnailFiles);
        }

        if (productDto.getImageUrls() != null && !productDto.getImageUrls().isEmpty()) {
            List<FileMaster> imageFiles = fileMasterRepository.findByUrlIn(productDto.getImageUrls());
            for (FileMaster file : imageFiles) {
                file.setProd(prod);
            }
            fileMasterRepository.saveAll(imageFiles);
        }
        return prod.getPno();
    }

  @Override
  public ProdDetailDto findBy(Long pno) {
      Prod product = prodRepository.findById(pno).orElseThrow(() -> new RuntimeException("Product not found"));
      List<ProdOption> options = prodOptionRepository.findByOptionMapsProdPno(pno);
      List<FileMaster> images = fileMasterRepository.findByProdPnoAndFileType(pno, FileMaster.FileType.prod_main);
      return entityToDto(product, options, images);
  }

  // @Override
  // public List<ProdDetailDto> findList() {
  //     List<Prod> products = prodRepository.findAll();
  //     return products.stream().map(product -> {
  //         List<ProdOption> options = prodOptionRepository.findByOptionMapsProdPno(product.getPno());
  //         List<FileMaster> images = fileMasterRepository.findByProdPnoAndFileType(product.getPno(), FileMaster.FileType.prod_main);
  //         return entityToDto(product, options, images);
  //     }).collect(Collectors.toList());
  // }

  // @Transactional
  // @Override
  // public Long modify(ProdDetailDto productDto) {
  //     Prod product = prodRepository.findById(productDto.getPno())
  //             .orElseThrow(() -> new RuntimeException("Product not found"));

  //     product.setTitle(productDto.getTitle());
  //     product.setContent(productDto.getContent());
  //     product.setPrice(productDto.getPrice());
  //     product.setStock(productDto.getStock());

  //     prodRepository.save(product);
  //     return product.getPno();
  // }

  // @Transactional
  // @Override
  // public boolean remove(Long pno) {
  //     if (!prodRepository.existsById(pno)) {
  //         return false;
  //     }
  //     prodRepository.deleteById(pno);
  //     return true;
  // }
  

  // private Prod dtoToEntity(ProdDetailDto dto) {
  //   return Prod.builder()
  //     .pno(dto.getPno())
  //     .title(dto.getTitle())
  //     .content(dto.getContent())
  //     .price(dto.getPrice())
  //     .stock(dto.getStock())
  //   .build();
  // }

  private ProdDetailDto entityToDto(Prod product, List<ProdOption> options, List<FileMaster> images) {
    List<ProdDetailDto.ProdOptionDto> optionDtos = options.stream()
      .map(opt -> 
        ProdDetailDto.ProdOptionDto.builder()
          .optionNo(opt.getNo())
          .type(opt.getType())
          .value(opt.getValue())
          .addPrice(opt.getAddPrice())
          .stock(opt.getOptionMaps().stream().mapToInt(ProdOptionMap::getStock).sum())
        .build())
      .collect(Collectors.toList());

    List<String> imageUrls = images.stream()
      .map(FileMaster::getUrl)
      .collect(Collectors.toList());

    return ProdDetailDto.builder()
      .pno(product.getPno())
      .title(product.getTitle())
      .content(product.getContent())
      .price(product.getPrice())
      .stock(product.getStock())
      .imageUrls(imageUrls)
      .options(optionDtos)
    .build();
  }



  @Override
  public Long modify(ProdDetailDto productDto) {
    Prod prod = prodRepository.findById(productDto.getPno()).orElseThrow(() -> new RuntimeException("상품이 존재하지않습니다"));
    prod.setTitle(productDto.getTitle());
    prod.setContent(productDto.getContent());
    prod.setPrice(productDto.getPrice());
    prod.setStock(productDto.getStock());

    prodRepository.save(prod);
List<ProdOption> existingOptions = prodOptionRepository.findByOptionMapsProdPno(prod.getPno());


for (ProdDetailDto.ProdOptionDto newOptionDto : productDto.getOptions()) {
    boolean isExisting = false;

    // 기존 옵션 리스트에서 같은 옵션이 있는지 확인
    // for (ProdOption existingOption : existingOptions) {
    //   if (existingOption.getNo().equals(newOptionDto.getOptionNo())) {
    //       // 기존 옵션 업데이트 (수정된 정보를 반영)
    //       existingOption = ProdOption.builder()
    //               .no(existingOption.getNo()) // 기존 옵션 유지
    //               .type(newOptionDto.getType())
    //               .value(newOptionDto.getValue())
    //               .addPrice(newOptionDto.getAddPrice())
    //               .optionMaps(existingOption.getOptionMaps()) // 기존 매핑 유지
    //               .build();
    //         isExisting = true;
    //         break;
    //     }
    // }
    // 3. 기존 옵션 수정
    for (ProdOption existingOption : existingOptions) {
      if (existingOption.getNo().equals(newOptionDto.getOptionNo())) {
          existingOption.updateOption(newOptionDto.getType(), newOptionDto.getValue(), newOptionDto.getAddPrice());
          isExisting = true;
          break;
      }
    }
    
    // 기존에 없는 새로운 옵션이면 추가
    if (!isExisting) {
        ProdOption newOption = ProdOption.builder()
                .type(newOptionDto.getType())
                .value(newOptionDto.getValue())
                .addPrice(newOptionDto.getAddPrice())
                .build();
        prodOptionRepository.save(newOption);

        // 브릿지 테이블(ProdOptionMap)도 추가하여 상품과 연결
        ProdOptionMap optionMap = ProdOptionMap.builder()
        .prod(prod) // 상품과 연결
        .option(newOption) // 옵션과 연결
        .stock(newOptionDto.getStock()) // 재고 설정
        .build();

        optionMapRepository.save(optionMap);
    }

 
}

    return prod.getPno();
  }

  @Override
  @Transactional
  public boolean remove(Long pno) {

      optionMapRepository.deleteByOptionNo(pno);


      prodOptionRepository.deleteOrphanOptions(pno);


      fileMasterRepository.deleteByProdNo(pno);


      prodRepository.deleteById(pno);

    return true;
  }

  @Override
  @Transactional
  public boolean removeOption(Long ono) {
    // ProdOption option 
       prodOptionRepository.findById(ono)
              .orElseThrow(() -> new RuntimeException("옵션이 존재하지 않습니다."));
  

      optionMapRepository.deleteById(ono);
  

      prodOptionRepository.deleteById(ono);
  
      return true;
  }
  
  
}
