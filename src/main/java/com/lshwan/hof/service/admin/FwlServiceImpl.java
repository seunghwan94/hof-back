package com.lshwan.hof.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.admin.FWL;
import com.lshwan.hof.repository.admin.FwlRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FwlServiceImpl implements FwlService{
  @Autowired
  private FwlRepository repository;

  @Override
  @Transactional
  public Long add(FWL fwl) {
    return repository.save(fwl).getFno();
  }

  @Override
  public FWL findBy(Long fno) {
    return repository.findById(fno).orElse(null);
  }

  @Override
  public List<FWL> findList() {
    
    return repository.findAll();
  }

  @Override
  @Transactional
  public Long modify(FWL fwl) {

    return repository.save(fwl).getFno();
  }

  @Override
  @Transactional
  public boolean remove(Long fno) {
    if (repository.existsById(fno)) {
      repository.deleteById(fno);
      return true;
    }
    return false;
  }
  
}
