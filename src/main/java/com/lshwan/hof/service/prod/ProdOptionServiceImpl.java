package com.lshwan.hof.service.prod;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.repository.prod.ProdOptionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdOptionServiceImpl implements ProdOptionService{
  private ProdOptionRepository repository;

  @Override
  @Transactional
  public ProdOption add(ProdOption prodOption) {
    return repository.save(prodOption);
  }

  @Override
  public ProdOption findBy(Long no) {
    return repository.findById(no).orElse(null);
  }

  @Override
  public List<ProdOption> findList() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Long modify(ProdOption prodOption) {
    return repository.save(prodOption).getNo();
  }

  @Override
  @Transactional
  public boolean remove(Long no) {
    if (repository.existsById(no)) {
      repository.delete(findBy(no));
      return true;
    }
    return false;
  }

  
}
