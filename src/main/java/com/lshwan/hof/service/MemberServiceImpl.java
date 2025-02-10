package com.lshwan.hof.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.member.MemberRepository;

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
