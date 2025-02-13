package com.lshwan.hof.service.prod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.repository.prod.ProdOptionMapRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdOptionMapServiceImpl implements ProdOptionMapService{
  @Autowired
  private ProdOptionMapRepository repository;

  @Override
  @Transactional
  public Long add(ProdOptionMap prodOptionMap) {
    return repository.save(prodOptionMap).getNo();
  }

  @Override
  public ProdOptionMap findBy(Long no) {
    return repository.findById(no).orElse(null);
  }

  @Override
  public List<ProdOptionMap> findList() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Long modify(ProdOptionMap prodOptionMap) {
    return repository.save(prodOptionMap).getNo();
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
