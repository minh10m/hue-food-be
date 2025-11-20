package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.TokenRecord;
import com.minh.Online.Food.Ordering.domain.ports.in.token.IssueTokensUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.token.RefreshAccessTokenUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.token.RevokeTokensUseCase;
import com.minh.Online.Food.Ordering.domain.ports.out.TokenGeneratorPort;
import com.minh.Online.Food.Ordering.domain.ports.out.TokenStorePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TokenUseCaseService implements IssueTokensUseCase, RefreshAccessTokenUseCase, RevokeTokensUseCase {

    private final TokenGeneratorPort jwt;
    private final TokenStorePort store;

    public TokenUseCaseService(TokenGeneratorPort jwt, TokenStorePort store) {
        this.jwt = jwt; this.store = store;
    }

    @Override
    @Transactional
    public Result issue(Long userId, String email, String role) {
        Instant now = Instant.now();
        store.revokeAllForUser(userId);

        String access = jwt.generateAccessToken(userId, email, role, now);
        String refresh = jwt.generateRefreshToken(userId, email, role, now);

        store.save(new TokenRecord(null, userId, access, TokenRecord.Type.ACCESS, false, jwt.expiresAt(access)));
        store.save(new TokenRecord(null, userId, refresh, TokenRecord.Type.REFRESH, false, jwt.expiresAt(refresh)));

        return new Result(access, refresh);
    }

    @Override
    @Transactional
    public String refresh(String refreshToken) {
        if (!jwt.isValid(refreshToken) || !store.isActive(refreshToken)) {
            throw new IllegalArgumentException("Invalid or revoked refresh token");
        }
        var claims = jwt.claims(refreshToken);
        Long uid = ((Number) claims.get("uid")).longValue();
        String email = (String) claims.get("sub");
        String role = (String) claims.get("role");

        String newAccess = jwt.generateAccessToken(uid, email, role, Instant.now());
        store.save(new TokenRecord(null, uid, newAccess, TokenRecord.Type.ACCESS, false, jwt.expiresAt(newAccess)));
        return newAccess;
    }

    @Override
    @Transactional
    public void revokeAll(Long userId) { store.revokeAllForUser(userId); }

    @Override
    @Transactional
    public void revokeOne(String token) { store.revoke(token); }
}
