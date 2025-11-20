package com.minh.Online.Food.Ordering.domain.ports.in.user;

import com.minh.Online.Food.Ordering.domain.model.UserAccount;

public interface RegisterUserUseCase {
    UserAccount register(String email, String rawPassword, String fullName);
}

