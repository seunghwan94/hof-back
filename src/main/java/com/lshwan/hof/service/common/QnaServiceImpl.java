package com.lshwan.hof.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.common.Qna;
import com.lshwan.hof.repository.common.QnaRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QnaServiceImpl implements QnaService{

  @Autowired
  QnaRepository repository;
  @Override
  @Transactional
  public Long add(Qna qna) {
      return repository.save(qna).getNo();
  }

  @Override
  public Qna findBy(Long no) {
      return repository.findById(no).orElse(null);
  }

  @Override
  public List<Qna> findList() {
      return repository.findAll();
  }

  @Override
  @Transactional
  public Long modify(Qna qna) {
      return repository.save(qna).getNo();
  }

  @Override
  @Transactional
  public boolean remove(Long no) {
      if (repository.existsById(no)) {
          repository.deleteById(no);
          return true;
      }
      return false;
  }
  
}
