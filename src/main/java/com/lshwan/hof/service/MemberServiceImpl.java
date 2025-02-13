package com.lshwan.hof.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.mapper.MemberMapper;
import com.lshwan.hof.repository.member.MemberRepository;

@Service
@AllArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
  private MemberRepository repository;
  private MemberMapper mapper;

  @Override
  public Long write(Member member) { 
    return repository.save(Member.builder().name("sssss").build()).getMno();
  }
  
  @Override
  public Member findBy(String id) {    
    return mapper.selectOne(id);
  }

  @Override
  public boolean login(String id, String pw) {
    Member m = findBy(id);
    return m != null && m.getPw().equals(pw);
  }

  @Override
  @Transactional
  public List<Member> findList() {
    // TODO Auto-generated method stub
    return repository.findAll();
  }

  
  
}
