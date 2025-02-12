package com.lshwan.hof.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final String secretKey = "your-secret-key";

    // 토큰 생성
    public String createToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    // 토큰에서 사용자 이름을 가져오는 메서드
    public String getUsername(String token) {
        try {
            JwtParser jwtParser = Jwts.parser()
                .setSigningKey(secretKey)  // 서명 검증 키 설정
                .build();

            return jwtParser.parseClaimsJws(token)  // 토큰 파싱
                    .getBody()
                    .getSubject();  // Claims에서 사용자 이름 가져오기
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token structure", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT token is empty or invalid", e);
        }
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();

            jwtParser.parseClaimsJws(token);  // 토큰 검증

            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
