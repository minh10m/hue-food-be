package com.minh.Online.Food.Ordering.adapters.persistence.user;

import com.minh.Online.Food.Ordering.domain.model.UserAccount;
import com.minh.Online.Food.Ordering.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository repo;

    public UserRepositoryAdapter(SpringDataUserRepository repo) { this.repo = repo; }

    @Override public boolean existsByEmail(String email) { return repo.existsByEmail(email); }

    @Override
    public UserAccount save(UserAccount u) {
        UserJpaEntity e = toEntity(u);
        UserJpaEntity s = repo.save(e);
        return toDomain(s);
    }

    @Override public Optional<UserAccount> findById(Long id) { return repo.findById(id).map(this::toDomain); }

    @Override public Optional<UserAccount> findByEmail(String email) { return repo.findByEmail(email).map(this::toDomain); }

    @Override
    public boolean existsById(Long userId) {
        return repo.existsById(userId);
    }

    private UserAccount toDomain(UserJpaEntity e) {
        return new UserAccount(e.getId(), e.getEmail(), e.getPasswordHash(), e.getFullName(),
                e.getAvatarUrl(), e.getRole(), e.isEnabled());
    }
    private UserJpaEntity toEntity(UserAccount u) {
        return UserJpaEntity.builder()
                .id(u.id()).email(u.email()).passwordHash(u.passwordHash())
                .fullName(u.fullName()).avatarUrl(u.avatarUrl()).role(u.role()).enabled(u.enabled())
                .build();
    }
}

