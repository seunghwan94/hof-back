package com.lshwan.hof.service.prod;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.repository.prod.ProdRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProdServiceImpl implements ProdService {
  private ProdRepository repository;

  @Override
  public Long add(Prod prod) {
    return repository.save(prod).getPno();
  }

  @Override
  public Prod findBy(Long pno) {
    return repository.findById(pno).get();
  }

  @Override
  public List<Prod> findList() {
    return repository.findAll();
  }

  @Override
  public Long modify(Prod prod) {
    return repository.save(prod).getPno();
  }

  @Override
  public Long remove(Long pno) {
    repository.delete(findBy(pno));
    return pno;
  }

}
