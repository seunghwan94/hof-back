package com.lshwan.hof.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
            .requestMatchers("index/**").permitAll()           
            .requestMatchers("file/**").permitAll()
            .requestMatchers("swagger-ui/**").permitAll()
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers("/grafana/**").permitAll()
            .requestMatchers("/actuator/prometheus").permitAll() 
            .anyRequest().authenticated() // 인증이 필요한 경우 설정
          );
        return http.build();
    }
}
