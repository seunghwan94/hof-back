package com.lshwan.hof.service.prod;

import java.util.List;

import com.lshwan.hof.domain.dto.prod.ProdDetailDto;


public interface ProdDetailService {
  Long add(ProdDetailDto productDto);

  ProdDetailDto findBy(Long pno);

  // List<ProdDetailDto> findList();

  Long modify(ProdDetailDto productDto);

  boolean remove(Long pno);

  boolean removeOption(Long ono);

  
}