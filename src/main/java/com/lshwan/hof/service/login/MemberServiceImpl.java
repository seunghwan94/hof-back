package com.lshwan.hof.service.login;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.mapper.MemberMapper;

import com.lshwan.hof.repository.member.MemberRepository;

@Service
@AllArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
  private MemberRepository repository;
  private MemberMapper mapper;
  private PasswordEncoder passwordEncoder;

  @Override
  public Long signUp(Member member) { 
    String passwordEndcoded = passwordEncoder.encode(member.getPw());
    member.setPw(passwordEndcoded);

    return write(member);

    // Member saveMember = repository.save(member);
    // return saveMember.getMno();
    // return repository.save(Member.builder().name("sssss").build()).getMno();
  }
  
  @Override
  public Long write(Member member) {
    return repository.save(member).getMno();
  }

  @Override
  public Member findBy(String id) {    
    return repository.findById(id).orElse(null);
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

  // @Override
  // public UserDetails loadUserByUsername(String username) {
  //   // DB에서 사용자 조회
  //   Member member = findBy(username);
  //   if (member == null) {
  //     throw new UsernameNotFoundException("회원 정보가 없습니다: " + username);
  //   }
  //   // UserDetails 객체로 반환
  //   return User.builder()
  //     .username(member.getId())   // 아이디
  //     .password(member.getPw())   // 비밀번호
  //     .roles(member.getRole().name())  // 역할
  //     .build();
  // }

  
  
}
