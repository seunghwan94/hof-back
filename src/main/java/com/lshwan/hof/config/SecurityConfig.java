package com.lshwan.hof.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.lshwan.hof.security.JwtAuthenticationFilter;
import com.lshwan.hof.service.login.CustomUserDetailsService;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@ConfigurationProperties(prefix = "custom") // application.yml에서 "custom"으로 시작하는 설정을 매핑
@Log4j2
public class SecurityConfig implements WebMvcConfigurer{

  
  @Bean
  public JwtTokenProvider jwtTokenProvider() {
    return new JwtTokenProvider();
  }

  private CustomUserDetailsService customUserDetailsService;

  public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")  // 모든 경로에 대해
        .allowedOrigins("http://localhost:3000","https://hof.lshwan.com")  // 외부 도메인에서의 요청 허용 (예시: React 앱)
        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메소드
        .allowedHeaders("*")  // 모든 헤더를 허용
        .allowCredentials(true);  // 쿠키나 인증 정보를 함께 보낼 수 있도록 설정
  }

  // 비밀번호 암호화
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    return authenticationManagerBuilder.build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (JWT 사용 시 필요 없음)
        .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안 함
      )
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/admin/**").permitAll()
        .requestMatchers("/main/**").permitAll()
        .requestMatchers("/login/**").permitAll()
        .requestMatchers("/signup/**","/signup/email/**").permitAll()
        .requestMatchers("/file/**").permitAll()
        .requestMatchers("/swagger-ui**","/swag**","/api-docs/**").permitAll()
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/common/**").permitAll()
        .requestMatchers("/jacoco/**").permitAll()
        .requestMatchers("/ws/**").permitAll()
        // .requestMatchers("/actuator/prometheus").permitAll() 
        // .anyRequest().authenticated() // 인증이 필요한 경우 설정
        .anyRequest().authenticated()
      )
      .formLogin(form -> form.disable()) // 폼 로그인 비활성화 (JWT만 사용)
      .logout(logout -> logout.disable())
      // 로그아웃 비활성화 (JWT만 사용)
      // .addFilterBefore(jwtAuthenticationFilter(jwtTokenProvider(), userDetailsService(passwordEncoder())), UsernamePasswordAuthenticationFilter.class); // JWT 필터 적용
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 적용
    log.info("SecurityFilterChain 설정 완료!!!");
    return http.build();
  }

  // In-Memory 사용자 인증 설정 (테스트용)
  // @Bean
  // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
  //   log.info("[SecurityConfig] 인메모리유저디테일매니저 생성 시작");

  //   UserDetails user1 = User.builder()
  //       .username("admin") // 관리자 계정
  //       .password(passwordEncoder.encode("admin123")) // 암호화된 비밀번호
  //       .roles("ADMIN") // ADMIN 권한 부여
  //       .build();

  //   UserDetails user2 = User.builder()
  //       .username("user") // 일반 사용자 계정
  //       .password(passwordEncoder.encode("user123")) // 암호화된 비밀번호
  //       .roles("USER") // USER 권한 부여
  //       .build();
  //   log.info("[SecurityConfig] 인메모리유저디테일매니저 생성 완료");
  //   return new InMemoryUserDetailsManager(user1, user2); // In-Memory 저장소에 사용자 정보 저장
  // }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
      // return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
      return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
  }

}
