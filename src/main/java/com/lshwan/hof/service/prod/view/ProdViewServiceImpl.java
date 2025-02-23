package com.lshwan.hof.service.prod.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.prod.view.ProdView;
import com.lshwan.hof.repository.prod.view.ProdViewRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdViewServiceImpl implements ProdViewService{
  private ProdViewRepository repository;

  @Override
  public ProdView findBy(Long pno) {
    return repository.findById(pno).get();
  }

  @Override
  public List<ProdView> findByCno(Long cno) {
    return repository.findByCno(cno);
  }

  @Override
  public List<ProdView> findByTitle(String title) {
    return repository.findByTitle(title);
  }

  @Override
  public List<ProdView> findList() {
    return repository.findAll(Sort.by(Sort.Direction.DESC, "pno"));
  }  
  
}
