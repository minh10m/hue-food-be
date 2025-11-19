package com.minh.Online.Food.Ordering.adapters.security;

import com.minh.Online.Food.Ordering.domain.ports.out.PasswordHasherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasherAdapter implements PasswordHasherPort {
    private final BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
    @Override public String hash(String raw) { return enc.encode(raw); }
    @Override public boolean matches(String raw, String hash) { return enc.matches(raw, hash); }
}
