package com.lshwan.hof.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.member.MemberRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService{
  
  @Autowired
  private MemberRepository memberRepository;
  // private final MemberRepository memberRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // DB에서 사용자 정보 조회
    Member member = memberRepository.findByLoginId(username)
      .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));


    // DB에서 가져온 회원 정보를 바탕으로 UserDetails 객체 반환
    return User.builder()
      .username(member.getId())  // id 필드
      .password(member.getPw())  // pw 필드
      .roles(member.getRole().name())  // role 필드 (Enum -> String)
      .build();
  }

  
}
