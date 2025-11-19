package com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password;


public interface ResetPasswordUseCase {
    void resetPassword(String email, String otp, String newPassword);
}


