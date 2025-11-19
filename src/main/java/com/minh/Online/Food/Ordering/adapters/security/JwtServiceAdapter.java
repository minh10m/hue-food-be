package com.minh.Online.Food.Ordering.adapters.security;

import com.minh.Online.Food.Ordering.config.JwtProperties;
import com.minh.Online.Food.Ordering.domain.ports.out.TokenGeneratorPort;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;

@Component
public class JwtServiceAdapter implements TokenGeneratorPort {

    private final SecretKey key;
    private final long accessTtl;
    private final long refreshTtl;

    public JwtServiceAdapter(JwtProperties props) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(props.getSecret()));
        this.accessTtl = props.getAccessTtlSeconds();
        this.refreshTtl = props.getRefreshTtlSeconds();
    }

    @Override
    public String generateAccessToken(Long userId, String email, String role, Instant now) {
        Instant exp = now.plusSeconds(accessTtl);
        return Jwts.builder()
                .setSubject(email)
                .claim("uid", userId)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(Long userId, String email, String role, Instant now) {
        Instant exp = now.plusSeconds(refreshTtl);
        return Jwts.builder()
                .setSubject(email)
                .claim("uid", userId)
                .claim("role", role)
                .claim("typ", "refresh")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isValid(String token) {
        try { Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); return true; }
        catch (JwtException | IllegalArgumentException e) { return false; }
    }

    @Override
    public Map<String, Object> claims(String token) {
        var c = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return new HashMap<>(c);
    }

    @Override
    public java.time.Instant expiresAt(String token) {
        var c = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return c.getExpiration().toInstant();
    }
}

