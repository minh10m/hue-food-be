package com.minh.Online.Food.Ordering.application.service;


import com.minh.Online.Food.Ordering.domain.model.UserAccount;
import com.minh.Online.Food.Ordering.domain.model.UserRole;
import com.minh.Online.Food.Ordering.domain.ports.in.user.GetUserProfileUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.user.RegisterUserUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.user.UpdateUserProfileUseCase;
import com.minh.Online.Food.Ordering.domain.ports.out.PasswordHasherPort;
import com.minh.Online.Food.Ordering.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserUseCaseService implements
       GetUserProfileUseCase, UpdateUserProfileUseCase, RegisterUserUseCase {

    private final UserRepositoryPort users;
    private final PasswordHasherPort hasher;

    public UserUseCaseService(UserRepositoryPort users, PasswordHasherPort hasher) {
        this.users = users; this.hasher = hasher;
    }

    @Override
    @Transactional
    public UserAccount register(String email, String rawPassword, String fullName) {
        if (email == null || rawPassword == null) throw new IllegalArgumentException("Email & password required");
        if (users.existsByEmail(email)) throw new IllegalArgumentException("Email already registered");
        String hash = hasher.hash(rawPassword);
        UserAccount toSave = new UserAccount(null, email, hash, fullName, null, UserRole.CUSTOMER, true);
        return users.save(toSave);
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

