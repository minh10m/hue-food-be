package com.minh.Online.Food.Ordering.domain.model;

import java.time.Instant;

public final class TokenRecord {
    public enum Type { ACCESS, REFRESH }

    private final Long id;
    private final Long userId;
    private final String token;
    private final Type type;
    private final boolean revoked;
    private final Instant expiresAt;

    public TokenRecord(Long id, Long userId, String token, Type type, boolean revoked, Instant expiresAt) {
        this.id = id; this.userId = userId; this.token = token; this.type = type; this.revoked = revoked; this.expiresAt = expiresAt;
    }

    public Long id() { return id; }
    public Long userId() { return userId; }
    public String token() { return token; }
    public Type type() { return type; }
    public boolean revoked() { return revoked; }
    public Instant expiresAt() { return expiresAt; }

    public TokenRecord withId(Long newId){ return new TokenRecord(newId, userId, token, type, revoked, expiresAt); }
    public TokenRecord revokedCopy(){ return new TokenRecord(id, userId, token, type, true, expiresAt); }
}

