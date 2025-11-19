package com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password;

public interface VerifyOtpUseCase {
    void verifyOtp(String email, String otp);
}


