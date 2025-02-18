package com.lshwan.hof.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lshwan.hof.config.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;
  // private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    log.info("JwtAuthenticationFilter 실행됨!!! 요청 URL: {}", request.getRequestURI());

    // 1 요청 헤더에서 Authorization 값을 가져옴
    String token = getTokenFromRequest(request);
    log.info("JWT 필터에서 받은 토큰: {}", token);

    if (token != null) {
      try {
        // 2️ 토큰 검증 및 사용자 정보 추출
        String username = jwtTokenProvider.validateExtract(token);
        log.info("JWT 검증 완료!!! username: {}", username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          log.info("UserDetailsService에서 조회한 사용자: {}", userDetails.getUsername());

          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
          log.info("SecurityContext에 사용자 인증 완료!!!");
        }
      } catch (Exception e) {
        log.error("JWT 검증 실패: {}", e.getMessage(), e);
        //250217
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid or expired token");
        return; // 이후 필터 체인 진행하지 않음
      }
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    log.info("getTokenFromRequest 값:" + bearerToken);
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }


}
