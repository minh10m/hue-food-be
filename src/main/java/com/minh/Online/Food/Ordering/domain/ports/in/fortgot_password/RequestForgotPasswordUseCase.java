package com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password;

public interface RequestForgotPasswordUseCase {
    void sendOtpToEmail(String email, String clientIp, String userAgent);
}

