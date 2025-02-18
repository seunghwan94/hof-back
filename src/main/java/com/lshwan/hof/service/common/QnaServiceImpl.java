package com.lshwan.hof.service.common;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.QnaDto;
import com.lshwan.hof.domain.entity.common.Qna;
import com.lshwan.hof.domain.entity.member.Member;
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
  public Long add(QnaDto dto,Member member,Qna parentQna) {
    Qna qna = toEntity(dto, member, parentQna); // 부모 Qna 포함하여 엔티티 변환
    return repository.save(qna).getNo();
  }

  @Override
  public Qna findBy(Long no) {
      return repository.findById(no).orElse(null);
  }

  @Override
  public List<QnaDto> findList() {
      return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Long modify(Qna qna) {
      return repository.save(qna).getNo();
  }

  @Override
  @Transactional
  public boolean remove(Long no) {
    // 자식 Qna(대댓글) 먼저 삭제
    repository.deleteByParentNo(no);
    
    // 부모 Qna 삭제
    if (repository.existsById(no)) {
        repository.deleteById(no);
        return true;
    }
    return false;
  }
  
}
