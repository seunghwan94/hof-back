package com.lshwan.hof.service.prod;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.repository.prod.ProdCategoryRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdCategoryServiceImpl implements ProdCategoryService{
  private ProdCategoryRepository repository;

  @Override
  @Transactional
  public Long add(ProdCategory prodCategory) {
    return repository.save(prodCategory).getCno();
  }

  @Override
  public ProdCategory findBy(Long cno) {
    return repository.findById(cno).orElse(null);
  }

  @Override
  public List<ProdCategory> findList() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Long modify(ProdCategory prodCategory) {
    return repository.save(prodCategory).getCno();
  }

  @Override
  @Transactional
  public boolean remove(Long cno) {
    if (repository.existsById(cno)) {
      repository.deleteById(cno);
      return true;
    }
    return false;
  }
  
}
