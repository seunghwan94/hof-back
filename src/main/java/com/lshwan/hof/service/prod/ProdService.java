package com.lshwan.hof.service.prod;

import java.util.List;

import com.lshwan.hof.domain.entity.prod.Prod;


public interface ProdService {
  Long add(Prod prod); 

  Prod findBy(Long pno);

  List<Prod> findList();

  Long modify(Prod prod);

  Long remove(Long pno);
}
