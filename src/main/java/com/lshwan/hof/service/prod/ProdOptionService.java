package com.lshwan.hof.service.prod;

import java.util.List;

import com.lshwan.hof.domain.entity.prod.ProdOption;

public interface ProdOptionService {
  ProdOption add(ProdOption prodOption); 

  ProdOption findBy(Long no);

  List<ProdOption> findList();

  Long modify(ProdOption prodOption);

  boolean remove(Long no);
}
