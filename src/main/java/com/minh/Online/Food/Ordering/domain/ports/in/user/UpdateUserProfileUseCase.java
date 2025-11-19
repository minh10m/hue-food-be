package com.minh.Online.Food.Ordering.domain.ports.in.user;

import com.minh.Online.Food.Ordering.domain.model.UserAccount;

import java.util.Optional;

public interface UpdateUserProfileUseCase {
    Optional<UserAccount> updateProfile(Long id, String fullName, String avatarUrl);
}
