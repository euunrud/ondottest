package com.ondot.ondot_back.global.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.Principal;
import java.util.Date;
import java.util.Objects;

@Component
@Getter
public class JwtTokenProvider {
    private final long JWT_ACCESS_TOKEN_EXPTIME;
    private final long JWT_REFRESH_TOKEN_EXPTIME;
    private final String JWT_ACCESS_SECRET_KEY;
    private final String JWT_REFRESH_SECRET_KEY;
    private Key accessKey;
    private Key refreshKey;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.time.access}") long JWT_ACCESS_TOKEN_EXPTIME,
                            @Value("${jwt.time.refresh}") long JWT_REFRESH_TOKEN_EXPTIME,
                            @Value("${jwt.secret.access}") String JWT_ACCESS_SECRET_KEY,
                            @Value("${jwt.secret.refresh}") String JWT_REFRESH_SECRET_KEY) {
        this.JWT_ACCESS_TOKEN_EXPTIME = JWT_ACCESS_TOKEN_EXPTIME;
        this.JWT_REFRESH_TOKEN_EXPTIME = JWT_REFRESH_TOKEN_EXPTIME;
        this.JWT_ACCESS_SECRET_KEY = JWT_ACCESS_SECRET_KEY;
        this.JWT_REFRESH_SECRET_KEY = JWT_REFRESH_SECRET_KEY;
    }

    @PostConstruct
    public void initialize() {
        byte[] accessKeyBytes = Decoders.BASE64.decode(JWT_ACCESS_SECRET_KEY);
        this.accessKey = Keys.hmacShaKeyFor(accessKeyBytes);

        byte[] secretKeyBytes = Decoders.BASE64.decode(JWT_REFRESH_SECRET_KEY);
        this.refreshKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String createAccessToken(Long userid) {
        System.out.println("JWT 토큰 생성");
        Claims claims = Jwts.claims().setSubject(userid.toString());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JWT_ACCESS_TOKEN_EXPTIME))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Long userid) {
        Claims claims = Jwts.claims().setSubject(userid.toString());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JWT_REFRESH_TOKEN_EXPTIME))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUseridFromAcs(String token) {
        return Jwts.parserBuilder().setSigningKey(accessKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getUseridFromRef(String token) {
        return Jwts.parserBuilder().setSigningKey(accessKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(accessToken).getBody().getExpiration();

        Long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    public static Long getUserIdFromPrincipal(Principal principal) {
        if (Objects.isNull(principal)) {
            //
//            throw new InvalidAuthException();
        }
        return Long.valueOf(principal.getName());
    }
}
