package com.minh.Online.Food.Ordering.domain.model;

import java.time.Instant;
import java.util.Objects;

public final class ForgotPassword {

    private final Long id;
    private final String email;
    private final String otp;
    private final Instant expiresAt;
    private final Instant createdAt;
    private final Instant usedAt;
    private final int attemptCount;

    public ForgotPassword(Long id,
                          String email,
                          String otp,
                          Instant expiresAt,
                          Instant createdAt,
                          Instant usedAt,
                          int attemptCount) {
        this.id = id;
        this.email = email;
        this.otp = otp;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
        this.attemptCount = attemptCount;
    }

    public Long id()           { return id; }
    public String email()      { return email; }
    public String otp()        { return otp; }
    public Instant expiresAt() { return expiresAt; }
    public Instant createdAt() { return createdAt; }
    public Instant usedAt()    { return usedAt; }
    public int attemptCount()  { return attemptCount; }

    public boolean isExpired(Instant now) {
        return expiresAt != null && now.isAfter(expiresAt);
    }

    public boolean isUsed() {
        return usedAt != null;
    }

    public ForgotPassword withId(Long newId) {
        return new ForgotPassword(newId, email, otp, expiresAt, createdAt, usedAt, attemptCount);
    }

    public ForgotPassword markUsed(Instant now) {
        return new ForgotPassword(id, email, otp, expiresAt, createdAt, now, attemptCount);
    }

    public ForgotPassword increaseAttempt() {
        return new ForgotPassword(id, email, otp, expiresAt, createdAt, usedAt, attemptCount + 1);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof ForgotPassword f) && Objects.equals(id, f.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

