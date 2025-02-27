package com.lshwan.hof.service.common;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.QnaDto;
import com.lshwan.hof.domain.entity.common.Qna;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.repository.common.QnaRepository;
import com.lshwan.hof.repository.prod.ProdRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QnaServiceImpl implements QnaService{

  @Autowired
  QnaRepository repository;
  @Autowired
  ProdRepository prodRepository;
  @Override
  @Transactional
  public Long add(QnaDto dto,Member member,Qna parentQna) {


    Prod product = prodRepository.findById(dto.getPno()).orElse(null);

    // 새로운 Qna 엔티티 생성 시, 답변이면 상태는 자동으로 처리후로 설정되도록 toEntity에서 처리
    Qna qna = toEntity(dto, member, parentQna,product);
    Long newId = repository.save(qna).getNo();
    
    // 만약 부모 Qna가 있다면 부모의 상태를 '처리후'로 업데이트
    if (parentQna != null) {
      parentQna.updateStatus(Qna.QnaStatus.처리후);
      repository.save(parentQna);
    }
    return newId;
  }

  @Override
  public Qna findBy(Long no) {
      return repository.findById(no).orElse(null);
  }

  @Override
  public List<QnaDto> findList() {


      List<QnaDto> dto = repository.findAll(Sort.by(Sort.Direction.DESC, "no"))
      .stream()
      .map(this::toDto)
      .collect(Collectors.toList());
      // dto.forEach(System.out::println);
      return dto;
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

  public List<QnaDto> findByProduct(Long pno) {
    List<Qna> qnaList = repository.findByPno(pno);
    return qnaList.stream().map(QnaDto::new).collect(Collectors.toList());
}
}
