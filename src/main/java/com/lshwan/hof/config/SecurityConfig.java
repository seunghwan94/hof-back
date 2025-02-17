package com.lshwan.hof.config;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.lshwan.hof.security.JwtAuthenticationFilter;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig implements WebMvcConfigurer{

  @Bean
  public JwtTokenProvider jwtTokenProvider() {
    return new JwtTokenProvider();
  }

  // 비밀번호 암호화
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("https://hof.lshwan.com","http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setExposedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    log.info("어던티케이션 빈 생성됨");
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (JWT 사용 시 필요 없음)
        .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안 함
      )
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .authorizeHttpRequests(auth -> auth
        // .requestMatchers("/admin/**").permitAll()
        .requestMatchers("/main/**").permitAll()
        .requestMatchers("/login/**","/api/v1/login").permitAll()
        // .requestMatchers("/file/**").permitAll()
        // .requestMatchers("/swagger-ui/**").permitAll()
        // .requestMatchers("/actuator/**").permitAll()
        // .requestMatchers("/actuator/prometheus").permitAll() 
        // .anyRequest().authenticated() // 인증이 필요한 경우 설정
        .anyRequest().authenticated()
      )
      .formLogin(form -> form.disable()) // 폼 로그인 비활성화 (JWT만 사용)
      .logout(logout -> logout.disable())
      // 로그아웃 비활성화 (JWT만 사용)
      .addFilterBefore(jwtAuthenticationFilter(jwtTokenProvider(), userDetailsService(passwordEncoder())), UsernamePasswordAuthenticationFilter.class); // JWT 필터 적용
    log.info("SecurityFilterChain 설정 완료!!!");
    return http.build();
  }

  // In-Memory 사용자 인증 설정 (테스트용)
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    log.info("[SecurityConfig] 인메모리유저디테일매니저 생성 시작");

    UserDetails user1 = User.builder()
        .username("admin") // 관리자 계정
        .password(passwordEncoder.encode("admin123")) // 암호화된 비밀번호
        .roles("ADMIN") // ADMIN 권한 부여
        .build();

    UserDetails user2 = User.builder()
        .username("user") // 일반 사용자 계정
        .password(passwordEncoder.encode("user123")) // 암호화된 비밀번호
        .roles("USER") // USER 권한 부여
        .build();
    log.info("[SecurityConfig] 인메모리유저디테일매니저 생성 완료");
    return new InMemoryUserDetailsManager(user1, user2); // In-Memory 저장소에 사용자 정보 저장
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
      return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
  }

}
