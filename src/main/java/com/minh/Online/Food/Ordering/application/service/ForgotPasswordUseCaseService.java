package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.ForgotPassword;
import com.minh.Online.Food.Ordering.domain.model.UserAccount;
import com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password.RequestForgotPasswordUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password.ResetPasswordUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.fortgot_password.VerifyOtpUseCase;
import com.minh.Online.Food.Ordering.domain.ports.out.ForgotPasswordRepositoryPort;
import com.minh.Online.Food.Ordering.domain.ports.out.PasswordHasherPort;
import com.minh.Online.Food.Ordering.domain.ports.out.SendEmailPort;
import com.minh.Online.Food.Ordering.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;

@Service
public class ForgotPasswordUseCaseService implements
        RequestForgotPasswordUseCase,
        VerifyOtpUseCase,
        ResetPasswordUseCase {

    private final ForgotPasswordRepositoryPort forgotRepo;
    private final SendEmailPort mailer;
    private final UserRepositoryPort users;
    private final PasswordHasherPort hasher;

    private final Duration otpTtl = Duration.ofMinutes(10);
    private final SecureRandom random = new SecureRandom();

    public ForgotPasswordUseCaseService(
            ForgotPasswordRepositoryPort forgotRepo,
            SendEmailPort mailer,
            UserRepositoryPort users,
            PasswordHasherPort hasher
    ) {
        this.forgotRepo = forgotRepo;
        this.mailer = mailer;
        this.users = users;
        this.hasher = hasher;
    }

    // 1) gửi OTP
    @Override
    @Transactional
    public void sendOtpToEmail(String email, String clientIp, String userAgent) {
        var maybeUser = users.findByEmail(email);
        if (maybeUser.isEmpty() || !maybeUser.get().enabled()) {
            // luôn im lặng -> controller vẫn trả 200
            return;
        }

        String otp = String.format("%06d", random.nextInt(1_000_000));
        Instant now = Instant.now();

        ForgotPassword record = new ForgotPassword(
                null,
                email,
                otp,
                now.plus(otpTtl),
                now,
                null,
                0
        );

        forgotRepo.save(record);

        String subject = "Mã OTP đặt lại mật khẩu";
        String html = """
                <p>Xin chào,</p>
                <p>Mã OTP để đặt lại mật khẩu tài khoản của bạn là:</p>
                <h2>%s</h2>
                <p>Mã OTP có hiệu lực trong %d phút.</p>
                <p>Nếu bạn không yêu cầu, hãy bỏ qua email này.</p>
                """.formatted(otp, otpTtl.toMinutes());

        mailer.send(email, subject, html);
    }

    // 2) verify OTP (nếu FE muốn call riêng)
    @Override
    @Transactional(readOnly = true)
    public void verifyOtp(String email, String otp) {
        Instant now = Instant.now();
        var record = forgotRepo.findValidByEmailAndOtp(email, otp, now)
                .orElseThrow(() -> new IllegalArgumentException("OTP không hợp lệ hoặc đã hết hạn"));

        if (record.isUsed() || record.isExpired(now)) {
            throw new IllegalArgumentException("OTP không hợp lệ hoặc đã hết hạn");
        }
    }

    // 3) reset password
    @Override
    @Transactional
    public void resetPassword(String email, String otp, String newPassword) {
        Instant now = Instant.now();
        var record = forgotRepo.findValidByEmailAndOtp(email, otp, now)
                .orElseThrow(() -> new IllegalArgumentException("OTP không hợp lệ hoặc đã hết hạn"));

        if (record.isUsed() || record.isExpired(now)) {
            throw new IllegalArgumentException("OTP không hợp lệ hoặc đã hết hạn");
        }

        UserAccount user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));

        String encoded = hasher.hash(newPassword);
        UserAccount updated = user.withPasswordHash(encoded);
        users.save(updated);

        forgotRepo.save(record.markUsed(now));
    }
}
