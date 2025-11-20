package com.minh.Online.Food.Ordering.adapters.persistence.token;

import com.minh.Online.Food.Ordering.domain.model.TokenRecord;
import com.minh.Online.Food.Ordering.domain.ports.out.TokenStorePort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenRepositoryAdapter implements TokenStorePort {

    private final SpringDataTokenRepository repo;

    public TokenRepositoryAdapter(SpringDataTokenRepository repo) { this.repo = repo; }

    @Override @Transactional
    public TokenRecord save(TokenRecord tr) {
        TokenJpaEntity e = TokenJpaEntity.builder()
                .id(tr.id())
                .userId(tr.userId())
                .token(tr.token())
                .type(tr.type() == TokenRecord.Type.ACCESS ? TokenJpaEntity.TokenType.ACCESS : TokenJpaEntity.TokenType.REFRESH)
                .revoked(tr.revoked())
                .expiresAt(tr.expiresAt())
                .build();
        var s = repo.save(e);
        return new TokenRecord(s.getId(), s.getUserId(), s.getToken(),
                s.getType() == TokenJpaEntity.TokenType.ACCESS ? TokenRecord.Type.ACCESS : TokenRecord.Type.REFRESH,
                s.isRevoked(), s.getExpiresAt());
    }

    @Override
    public Optional<TokenRecord> findByToken(String token) {
        return repo.findByToken(token).map(s -> new TokenRecord(
                s.getId(), s.getUserId(), s.getToken(),
                s.getType() == TokenJpaEntity.TokenType.ACCESS ? TokenRecord.Type.ACCESS : TokenRecord.Type.REFRESH, // typo guard below
                s.isRevoked(), s.getExpiresAt()
        ));
    }

    @Override @Transactional
    public void revokeAllForUser(Long userId) { repo.revokeAllByUserId(userId); }

    @Override @Transactional
    public void revoke(String token) { repo.revokeOne(token); }

    @Override
    public boolean isActive(String token) { return repo.isActive(token, Instant.now()); }
}

