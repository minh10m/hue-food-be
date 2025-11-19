package com.minh.Online.Food.Ordering.domain.ports.out;

import java.time.Instant;
import java.util.Map;

public interface TokenGeneratorPort {
    String generateAccessToken(Long userId, String email, String role, Instant now);
    String generateRefreshToken(Long userId, String email, String role, Instant now);
    boolean isValid(String token);
    Map<String, Object> claims(String token);
    Instant expiresAt(String token);
}
