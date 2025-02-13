package com.lshwan.hof.service.prod;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.repository.prod.ProdRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdServiceImpl implements ProdService {
  private ProdRepository repository;

  @Override
  @Transactional
  public Long add(Prod prod) {
    return repository.save(prod).getPno();
  }

  @Override
  public Prod findBy(Long pno) {
    return repository.findById(pno).orElse(null);
  }

  @Override
  public List<Prod> findList() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Long modify(Prod prod) {
    return repository.save(prod).getPno();
  }

  @Override
  @Transactional
  public boolean remove(Long pno) {
    if (repository.existsById(pno)) {
      repository.delete(findBy(pno));
      return true;
    }
    return false;
  }

}
