package com.example.usermanagement.security;

import static com.example.usermanagement.security.Secret.JWTConfig.*;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;
    private final Key secretKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private final long tokenValidMillisecond = JWT_VALID_TIME;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 전달받은 userId로 해당 회원에 대한 jwt 생성
     * @param userId
     * @param roles
     * @return
     */
    public String createToken(Long userId, List<String> roles) {
        /*
         * {
         *     "aud" : {audience},
         *     "iat" : {issuedAt},
         *     "exp" : {now} + {tokenValidMillisecond},
         *     "user_id": {userId},
         *     "roles" : {roles}
         * }
         */
        Date now = new Date();
        // registered claim
        Claims claims = Jwts.claims()
                .setAudience(JWT_AUDIENCE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond));

        // public claim
        claims.put("user_id", userId);
        claims.put("roles", roles);

        // token 생성
        // TODO header는 따로 생성 안 해도 되나?
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();

        return token;
    }

    /**
     * token을 읽어 접근하려는 유저 확인
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.extractUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * token에서 username 추출
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        Long userId = (Long)Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .requireAudience(JWT_AUDIENCE)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user_id");

        return userId.toString();
    }

    // request에서 토큰을 뽑아내 전달한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(JWT_FIELD_IN_HEADER);
    }

    // 토큰의 유효기간 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .requireAudience(JWT_AUDIENCE)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
