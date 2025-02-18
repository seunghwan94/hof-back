package com.lshwan.hof.service.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  @Autowired
  private MemberRepository repository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public Long write(Member member) { 
    if(repository.findByLoginId(member.getId()).isPresent()) {
      throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
    }
    member.setPw(passwordEncoder.encode(member.getPw()));

    Member signupMember = repository.save(member);

    return signupMember.getMno();
  }
  

  @Override
  public Member findBy(String id) {    
    return repository.findByLoginId(id).orElse(null);
  }

  @Override
  public boolean login(String id, String pw) {
    Member m = findBy(id);
    return m != null && passwordEncoder.matches(pw, m.getPw());
  }

  @Override
  @Transactional
  public List<Member> findList() {
    return repository.findAll();
  }
  
}
