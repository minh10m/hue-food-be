package com.minh.Online.Food.Ordering.domain.ports.out;

import com.minh.Online.Food.Ordering.domain.model.UserAccount;

import java.util.Optional;

public interface UserRepositoryPort {
    boolean existsByEmail(String email);
    UserAccount save(UserAccount u);
    Optional<UserAccount> findById(Long id);
    Optional<UserAccount> findByEmail(String email);

    boolean existsById(Long userId);
}
