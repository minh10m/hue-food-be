package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.LoginRequest;
import com.minh.Online.Food.Ordering.domain.ports.in.token.IssueTokensUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.token.RefreshAccessTokenUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.token.RevokeTokensUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.user.GetUserProfileUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.user.RegisterUserUseCase;
import com.minh.Online.Food.Ordering.domain.ports.out.PasswordHasherPort;
import com.minh.Online.Food.Ordering.adapters.web.dto.AuthenticationResponse;
import com.minh.Online.Food.Ordering.adapters.web.dto.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final RegisterUserUseCase registerUC;
    private final GetUserProfileUseCase getUserUC;
    private final PasswordHasherPort passwordHasher;
    private final IssueTokensUseCase issueTokensUC;
    private final RefreshAccessTokenUseCase refreshUC;
    private final RevokeTokensUseCase revokeUC;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@Valid @RequestBody SignupRequest req) {
        // 1) Tạo user (hash pass trong use-case register)
        var user = registerUC.register(req.getEmail(), req.getPassword(), req.getFullName());

        // 2) Cấp token
        var tokens = issueTokensUC.issue(user.id(), user.email(), user.role().name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(tokens.accessToken(), tokens.refreshToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest req) {
        var userOpt = getUserUC.getByEmail(req.getEmail());
        if (userOpt.isEmpty() || !userOpt.get().enabled())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var user = userOpt.get();
        if (!passwordHasher.matches(req.getPassword(), user.passwordHash()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var tokens = issueTokensUC.issue(user.id(), user.email(), user.role().name());
        return ResponseEntity.ok(new AuthenticationResponse(tokens.accessToken(), tokens.refreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestParam("refreshToken") String refreshToken) {
        String newAccess = refreshUC.refresh(refreshToken);
        return ResponseEntity.ok(new AuthenticationResponse(newAccess, refreshToken));
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<Void> logout(@PathVariable Long userId) {
        revokeUC.revokeAll(userId);
        return ResponseEntity.noContent().build();
    }
}

