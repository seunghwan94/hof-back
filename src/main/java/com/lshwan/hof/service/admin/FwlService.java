package com.lshwan.hof.service.admin;

import java.util.List;

import com.lshwan.hof.domain.entity.admin.FWL;

public interface FwlService {
  Long add (FWL fwl);

  FWL findBy(Long fno);

  List<FWL> findList();

  Long modify(FWL fwl);

  boolean remove(Long fno);
}
