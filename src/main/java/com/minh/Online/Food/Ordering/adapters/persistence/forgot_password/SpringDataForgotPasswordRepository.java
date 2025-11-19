package com.minh.Online.Food.Ordering.adapters.persistence.forgot_password;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface SpringDataForgotPasswordRepository extends JpaRepository<ForgotPasswordJpaEntity, Long> {

    @Query("""
           select f from ForgotPasswordJpaEntity f
           where f.email = :email
           order by f.createdAt desc
           """)
    Optional<ForgotPasswordJpaEntity> findTopByEmailOrderByCreatedAtDesc(@Param("email") String email);

    @Query("""
           select f from ForgotPasswordJpaEntity f
           where f.email = :email
             and f.otp = :otp
             and f.expiresAt >= :now
           order by f.createdAt desc
           """)
    Optional<ForgotPasswordJpaEntity> findValidByEmailAndOtp(@Param("email") String email,
                                                             @Param("otp") String otp,
                                                             @Param("now") Instant now);
}

