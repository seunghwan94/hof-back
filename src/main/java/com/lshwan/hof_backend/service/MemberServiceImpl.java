package com.lshwan.hof_backend.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import com.lshwan.hof_backend.domain.entity.Member;
import com.lshwan.hof_backend.repository.MemberRepository;

@Service
@AllArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
  private MemberRepository repository;

  @Override
  public Long write(Member member) {
    return repository.save(Member.builder().name("sssss").build()).getMno();
  }
  
}
