package com.minh.Online.Food.Ordering.application.service;


import com.minh.Online.Food.Ordering.domain.model.UserAccount;
import com.minh.Online.Food.Ordering.domain.model.UserRole;
import com.minh.Online.Food.Ordering.domain.ports.in.user.GetUserProfileUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.user.UpdateUserProfileUseCase;
import com.minh.Online.Food.Ordering.domain.ports.out.PasswordHasherPort;
import com.minh.Online.Food.Ordering.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserUseCaseService implements
       GetUserProfileUseCase, UpdateUserProfileUseCase {

    private final UserRepositoryPort users;
    private final PasswordHasherPort hasher;

    public UserUseCaseService(UserRepositoryPort users, PasswordHasherPort hasher) {
        this.users = users; this.hasher = hasher;
    }


    @Override public Optional<UserAccount> getById(Long id) { return users.findById(id); }
    @Override public Optional<UserAccount> getByEmail(String email) { return users.findByEmail(email); }

    @Override
    @Transactional
    public Optional<UserAccount> updateProfile(Long id, String fullName, String avatarUrl) {
        return users.findById(id).map(u -> {
            String finalAvatar = avatarUrl;
            // nếu avatarUrl trống và bạn nhận file ảnh từ adapter, hãy gọi avatarStorage.store(...) ở adapter trước, rồi truyền url vào đây
            UserAccount merged = u.withProfile(
                    fullName != null ? fullName : u.fullName(),
                    finalAvatar != null ? finalAvatar : u.avatarUrl()
            );
            return users.save(merged);
        });
    }
}

