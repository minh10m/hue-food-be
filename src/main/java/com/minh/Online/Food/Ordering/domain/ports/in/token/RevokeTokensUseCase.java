package com.minh.Online.Food.Ordering.domain.ports.in.token;

public interface RevokeTokensUseCase {
    void revokeAll(Long userId);
    void revokeOne(String token);
}
