package com.lshwan.hof.service.prod;

import java.util.List;

import com.lshwan.hof.domain.entity.prod.ProdCategory;

public interface ProdCategoryService {
  Long add(ProdCategory prodCategory); 

  ProdCategory findBy(Long cno);

  List<ProdCategory> findList();

  Long modify(ProdCategory prodCategory);

  boolean remove(Long cno);
}
