package com.lshwan.hof.service.prod;


import com.lshwan.hof.domain.dto.PageRequestDto;
import com.lshwan.hof.domain.dto.PageResultDto;
import com.lshwan.hof.domain.dto.ProdDto;
import com.lshwan.hof.domain.entity.prod.Prod;


public interface ProdService {
  Prod add(Prod prod); 

  Prod findBy(Long pno);

  Prod findByTitle(String title);

  PageResultDto<ProdDto, Prod> findList(PageRequestDto dto);

  Long modify(Prod prod);

  boolean remove(Long pno);
}
