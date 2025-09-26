package com.minh.Online.Food.Ordering.modules.forgot_password;

import com.minh.Online.Food.Ordering.modules.forgot_password.dto.ChangePassword;
import com.minh.Online.Food.Ordering.modules.forgot_password.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/verify-email/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        return forgotPasswordService.verifyEmail(email);
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        return forgotPasswordService.verifyOtp(otp, email);
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {
        return forgotPasswordService.changePassword(changePassword, email);
    }

}
