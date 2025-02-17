package com.lshwan.hof.service.prod;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.PageRequestDto;
import com.lshwan.hof.domain.dto.PageResultDto;
import com.lshwan.hof.domain.dto.ProdDto;
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
  public Prod add(Prod prod) {
    return repository.save(prod);
  }

  @Override
  public Prod findBy(Long pno) {
    return repository.findById(pno).orElse(null);
  }
  

  @Override
  public Prod findByTitle(String title) {
    return repository.findByTitle(title).orElse(null);
  }
  @Override
  public PageResultDto<ProdDto, Prod> findList(PageRequestDto dto) {
      Pageable pageable = dto.getPageable(Sort.by(Sort.Direction.DESC, "pno"));
  
      Page<Prod> result = repository.findAll(pageable);
  
      Function<Prod, ProdDto> fn = (prod -> new ProdDto(
              prod.getPno(),
              prod.getTitle(),
              prod.getPrice(),
              prod.getStock(),
              prod.getCno() != null ? prod.getCno().getType().name() : null 
      ));
  
      return new PageResultDto<>(result, fn);
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
