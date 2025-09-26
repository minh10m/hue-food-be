package com.minh.Online.Food.Ordering.modules.forgot_password.service;

import com.minh.Online.Food.Ordering.modules.forgot_password.ForgotPassword;
import com.minh.Online.Food.Ordering.modules.forgot_password.ForgotPasswordRepository;
import com.minh.Online.Food.Ordering.modules.forgot_password.dto.ChangePassword;
import com.minh.Online.Food.Ordering.modules.forgot_password.dto.MailBody;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordService(UserRepository userRepository, EmailService emailService,
                                 ForgotPasswordRepository forgetPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgetPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> verifyEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email does not exist"));

        int otp = generateOtp();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password: " + otp)
                .subject("OTP for request forgot password")
                .build();

        ForgotPassword fp = forgotPasswordRepository.findByUser(user)
                .map(existing -> {
                    existing.setOtp(otp);
                    existing.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000));
                    return existing;
                })
                .orElseGet(() -> ForgotPassword.builder()
                        .otp(otp)
                        .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                        .user(user)
                        .build());

        emailService.sendEmail(mailBody);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification");
    }

    public ResponseEntity<String> verifyOtp(Integer otp, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByUserAndOtp(otp, user)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid OTP for email: " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.delete(fp);
            return new ResponseEntity<>("OTP expired", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified");
    }

    public ResponseEntity<String> changePassword(ChangePassword changePassword, String email) {
        if (!changePassword.password().equals(changePassword.repeatPassword())) {
            return new ResponseEntity<>("Please enter the password again", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password changed successfully");
    }

    private Integer generateOtp() {
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }
}
