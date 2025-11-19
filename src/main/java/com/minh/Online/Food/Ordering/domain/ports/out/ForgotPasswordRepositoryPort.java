package com.minh.Online.Food.Ordering.domain.ports.out;


import com.minh.Online.Food.Ordering.domain.model.ForgotPassword;

import java.time.Instant;
import java.util.Optional;

public interface ForgotPasswordRepositoryPort {

    ForgotPassword save(ForgotPassword model);

    Optional<ForgotPassword> findLatestByEmail(String email);

    Optional<ForgotPassword> findValidByEmailAndOtp(String email, String otp, Instant now);
}


