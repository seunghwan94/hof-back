package com.lshwan.hof.service.prod;

import java.util.List;

import com.lshwan.hof.domain.entity.prod.Prod;


public interface ProdService {
  Prod add(Prod prod); 

  Prod findBy(Long pno);

  Prod findByTitle(String title);

  List<Prod> findList();

  Long modify(Prod prod);

  boolean remove(Long pno);
}
