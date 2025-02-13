package com.lshwan.hof.service.prod;

import java.util.List;

import com.lshwan.hof.domain.entity.prod.ProdOptionMap;

public interface ProdOptionMapService {
  Long add(ProdOptionMap prodOptionMap); 

  ProdOptionMap findBy(Long no);

  List<ProdOptionMap> findList();

  Long modify(ProdOptionMap prodOptionMap);

  boolean remove(Long no);
}
