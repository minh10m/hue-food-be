package com.minh.Online.Food.Ordering.adapters.web.mapper;

import com.minh.Online.Food.Ordering.adapters.web.dto.ForgotPasswordRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.ResetPasswordRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.VerifyOtpRequest;

public final class ForgotPasswordWebMapper {

    private ForgotPasswordWebMapper(){}

    public static String email(ForgotPasswordRequest req) {
        return normalizeEmail(req.getEmail());
    }

    public static String email(VerifyOtpRequest req) {
        return normalizeEmail(req.getEmail());
    }

    public static String email(ResetPasswordRequest req) {
        return normalizeEmail(req.getEmail());
    }

    public static String otp(VerifyOtpRequest req) {
        return req.getOtp().trim();
    }

    public static String otp(ResetPasswordRequest req) {
        return req.getOtp().trim();
    }

    public static String newPassword(ResetPasswordRequest req) {
        return req.getNewPassword();
    }

    private static String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
