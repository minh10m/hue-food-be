package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.ForgotPasswordRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.ResetPasswordRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.VerifyOtpRequest;
import com.minh.Online.Food.Ordering.adapters.web.mapper.ForgotPasswordWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password.RequestForgotPasswordUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password.ResetPasswordUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password.VerifyOtpUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/forgot-password")
public class ForgotPasswordController {

    private final RequestForgotPasswordUseCase requestUseCase;
    private final VerifyOtpUseCase verifyUseCase;
    private final ResetPasswordUseCase resetUseCase;

    @PostMapping
    public ResponseEntity<?> requestOtp(@Valid @RequestBody ForgotPasswordRequest req,
                                        HttpServletRequest http) {
        String email = ForgotPasswordWebMapper.email(req);
        String ip = http.getRemoteAddr();
        String ua = http.getHeader("User-Agent");
        requestUseCase.sendOtpToEmail(email, ip, ua);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest req) {
        String email = ForgotPasswordWebMapper.email(req);
        String otp = ForgotPasswordWebMapper.otp(req);
        verifyUseCase.verifyOtp(email, otp);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        String email = ForgotPasswordWebMapper.email(req);
        String otp = ForgotPasswordWebMapper.otp(req);
        String newPassword = ForgotPasswordWebMapper.newPassword(req);
        resetUseCase.resetPassword(email, otp, newPassword);
        return ResponseEntity.ok().build();
    }
}

