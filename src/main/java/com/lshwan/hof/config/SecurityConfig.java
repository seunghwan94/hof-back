package com.lshwan.hof.config;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.lshwan.hof.security.JwtAuthenticationFilter;
import com.lshwan.hof.service.login.CustomUserDetailsService;
// import com.lshwan.hof.service.social.CustomOAuth2UserService;
// import com.lshwan.hof.service.social.OAuth2UserDetailsService;
import com.lshwan.hof.service.social.CustomOAuth2UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
  // @Autowired
  private JwtTokenProvider jwtTokenProvider;
  
  private CustomUserDetailsService customUserDetailsService;
  private final CustomOAuth2UserService customOAuth2UserService;

  public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomOAuth2UserService customOAuth2UserService) {
      this.customUserDetailsService = customUserDetailsService;
      this.customOAuth2UserService = customOAuth2UserService;
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
      .cors(cors -> cors.configurationSource(request -> {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "https://hof.lshwan.com"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        return config;
        }))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/admin/**").permitAll()
        .requestMatchers("/main/**").permitAll()
        .requestMatchers("/login/**").permitAll()
        .requestMatchers("/login/oauth2/code/google").permitAll()
        .requestMatchers("/signup/**","/signup/email/**").permitAll()
        .requestMatchers("/file/**").permitAll()
        .requestMatchers("/swagger-ui/**","/swag/**","/api-docs/**").permitAll()
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

        .oauth2Login(oauth2 -> oauth2
        .successHandler((request, response, authentication) -> {
          OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
      
          // OAuth2 제공자로부터 받은 유저 ID (Google의 경우 "sub" 필드)
          String sub = oauthUser.getAttribute("sub");
          String provider = "google"; // 현재는 Google만 적용
      
          // JWT 발급
          String jwt = jwtTokenProvider.createToken(sub, provider);
      
          // 응답 헤더에 JWT 추가
          response.addHeader("Authorization", "Bearer " + jwt);
          log.info("OAuth2 로그인 성공: JWT 발급 완료. 사용자 ID={}, Provider={}", sub, provider);
      
          // 리디렉션 (프론트엔드로 이동)
          response.sendRedirect("/dashboard");
        })
          .userInfoEndpoint(userInfo -> userInfo
              .userService(customOAuth2UserService))
      )
      // 로그아웃 비활성화 (JWT만 사용)
      // .addFilterBefore(jwtAuthenticationFilter(jwtTokenProvider(), userDetailsService(passwordEncoder())), UsernamePasswordAuthenticationFilter.class); // JWT 필터 적용
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 적용
    log.info("SecurityFilterChain 설정 완료!!!");
    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
      // return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
      return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
  }

  @Bean
  public FilterRegistrationBean<OncePerRequestFilter> coopHeaderFilter() {
    FilterRegistrationBean<OncePerRequestFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new OncePerRequestFilter() {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
                throws ServletException, IOException {
            response.setHeader("Cross-Origin-Opener-Policy", "same-origin-allow-popups");
            filterChain.doFilter(request, response);
        }
    });
    filter.setOrder(-101);  // Security FilterChain 앞에서 실행되도록 우선순위 설정
    return filter;
  }
}
