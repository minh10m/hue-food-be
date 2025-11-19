package com.minh.Online.Food.Ordering.adapters.persistence.forgot_password;

import com.minh.Online.Food.Ordering.domain.model.ForgotPassword;
import com.minh.Online.Food.Ordering.domain.ports.out.ForgotPasswordRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;


@Component
public class ForgotPasswordRepositoryAdapter implements ForgotPasswordRepositoryPort {

    private final SpringDataForgotPasswordRepository repo;

    public ForgotPasswordRepositoryAdapter(SpringDataForgotPasswordRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public ForgotPassword save(ForgotPassword model) {
        ForgotPasswordJpaEntity e = toEntity(model);
        ForgotPasswordJpaEntity saved = repo.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<ForgotPassword> findLatestByEmail(String email) {
        return repo.findTopByEmailOrderByCreatedAtDesc(email)
                .map(this::toDomain);
    }

    @Override
    public Optional<ForgotPassword> findValidByEmailAndOtp(String email, String otp, Instant now) {
        return repo.findValidByEmailAndOtp(email, otp, now)
                .map(this::toDomain);
    }

   private ForgotPassword toDomain(ForgotPasswordJpaEntity e) {
        return new ForgotPassword(
                e.getId(),
                e.getEmail(),
                e.getOtp(),
                e.getExpiresAt(),
                e.getCreatedAt(),
                e.getUsedAt(),
                e.getAttemptCount()
        );
    }

    private ForgotPasswordJpaEntity toEntity(ForgotPassword d) {
        return ForgotPasswordJpaEntity.builder()
                .id(d.id())
                .email(d.email())
                .otp(d.otp())
                .expiresAt(d.expiresAt())
                .createdAt(d.createdAt())
                .usedAt(d.usedAt())
                .attemptCount(d.attemptCount())
                .build();
    }

}

