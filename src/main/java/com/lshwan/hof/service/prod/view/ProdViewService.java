package com.lshwan.hof.service.prod.view;

import java.util.List;

import com.lshwan.hof.domain.entity.prod.view.ProdView;

public interface ProdViewService {

  ProdView findBy(Long pno);

  List<ProdView> findByTitle(String title);

  List<ProdView> findByCno(Long cno);

  List<ProdView> findList();
  
}
