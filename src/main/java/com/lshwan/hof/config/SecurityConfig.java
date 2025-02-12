package com.lshwan.hof.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.lshwan.hof.security.JwtAuthenticationFilter;
import com.lshwan.hof.config.JwtTokenProvider;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{
  private JwtTokenProvider jwtTokenProvider;

  public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

   @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해
            .allowedOrigins("http://localhost:3000")  // 외부 도메인에서의 요청 허용 (예시: React 앱)
            .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메소드
            .allowedHeaders("*")  // 모든 헤더를 허용
            .allowCredentials(true);  // 쿠키나 인증 정보를 함께 보낼 수 있도록 설정
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder 사용
    }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("index/**").permitAll()           
          .requestMatchers("file/**").permitAll()
          .requestMatchers("swagger-ui/**").permitAll()
          .requestMatchers("/actuator/**").permitAll()            
          // .requestMatchers("/actuator/prometheus").permitAll() 
          .anyRequest().authenticated() // 인증이 필요한 경우 설정
        )
        .formLogin(form -> form
          .loginPage("/login").permitAll()
          .defaultSuccessUrl("/intro", true)
          .failureUrl("/login?error=true")
        )
        .logout(logout -> logout
          .logoutUrl("/logout")
          .logoutSuccessUrl("/login")
        );
      return http.build();
  }
}
