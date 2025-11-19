package com.minh.Online.Food.Ordering.domain.ports.out;

import com.minh.Online.Food.Ordering.domain.model.TokenRecord;
import java.util.Optional;

public interface TokenStorePort {
    TokenRecord save(TokenRecord token);
    Optional<TokenRecord> findByToken(String token);
    void revokeAllForUser(Long userId);
    void revoke(String token);
    boolean isActive(String token); // !revoked && not expired (do adapter tính)
}
