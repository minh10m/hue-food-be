package com.minh.Online.Food.Ordering.domain.ports.in.user;

import com.minh.Online.Food.Ordering.domain.model.UserAccount;

import java.util.Optional;

public interface GetUserProfileUseCase {
    Optional<UserAccount> getById(Long id); Optional<UserAccount> getByEmail(String email);
}
