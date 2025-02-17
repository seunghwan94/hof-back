package com.lshwan.hof.service.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lshwan.hof.repository.member.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
  private MemberRepository memberRepository;
  private PasswordEncoder passwordEncoder;
  
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  
}
