package com.lshwan.hof.service.prod;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.prod.ProdDetailDto;
import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.repository.common.FileMasterRepository;
import com.lshwan.hof.repository.prod.ProdOptionRepository;
import com.lshwan.hof.repository.prod.ProdRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdDetailServiceImpl implements ProdDetailService {
    
  private final ProdRepository prodRepository;
  private final ProdOptionRepository prodOptionRepository;
  // private final ProdOptionMapRepository productOptionMapRepository;
  private final FileMasterRepository fileMasterRepository;
  
  // @Transactional
  // @Override
  // public Long add(ProdDetailDto productDto) {
  //     Prod product = dtoToEntity(productDto);
  //     prodRepository.save(product);
  //     return product.getPno();
  // }

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
}
