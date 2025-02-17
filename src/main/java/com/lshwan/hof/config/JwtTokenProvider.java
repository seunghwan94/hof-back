package com.lshwan.hof.config;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

import com.lshwan.hof.domain.entity.member.Member.MemberRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.ZonedDateTime;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
@Log4j2
public class JwtTokenProvider {
  // 시크릿 키 (임시, 환경 변수나 안전한 저장소에 보관해야 함)
  private String secretkey = "hjham1234567890hjham1234567890hjham1234567890";
  private SecretKey key = Keys.hmacShaKeyFor(secretkey.getBytes());

  /**
   * JWT 토큰 생성 메서드
   * @param content - 토큰에 저장할 사용자 정보
   * @return 생성된 JWT 토큰 문자열
   */
  public String generateToken(String content/* , MemberRole role */) {
    return Jwts.builder()
      .issuedAt(new Date()) // 토큰 발급 시간 설정
      .expiration(Date.from(ZonedDateTime.now().plusMonths(1L).toInstant())) // 만료 시간 설정 (1개월 후)
      .claim("sub", content) // 사용자 정보 저장 (Subject 클레임 사용)
      // .claim("role", role.name())/* role추가 */
      .signWith(key) // HMAC SHA 키를 사용한 서명
      .compact();
  }

  /**
   * JWT 토큰 검증 및 내용 추출
   * @param tokenStr - 클라이언트에서 받은 JWT 토큰
   * @return 토큰에서 추출한 사용자 정보 (유효하지 않으면 null 반환)
   */
  public String validateExtract(String tokenStr) {
    String contentValue = null;
    
    try {
      // JWT 파싱 및 검증
      Jws<Claims> defaultJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(tokenStr);
      Claims claims = defaultJws.getPayload();
      log.info("JWT 토큰 해석 성공!!! username: {}", claims.getSubject());
      
      log.info("Subject: {}", claims.getSubject());
      log.info("Issued At: {}", claims.getIssuedAt());
      log.info("Expiration: {}", claims.getExpiration());
      contentValue = claims.getSubject();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("JWT 검증 실패: {}", e.getMessage());
    }
    return contentValue;
  }
  
}
