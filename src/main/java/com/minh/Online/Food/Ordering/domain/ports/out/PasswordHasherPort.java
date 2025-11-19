package com.minh.Online.Food.Ordering.domain.ports.out;

public interface PasswordHasherPort {
    String hash(String raw);
    boolean matches(String raw, String hash);
}