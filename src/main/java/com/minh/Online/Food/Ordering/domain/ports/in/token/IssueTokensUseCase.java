package com.minh.Online.Food.Ordering.domain.ports.in.token;

public interface IssueTokensUseCase {
    record Result(String accessToken, String refreshToken) {}
    Result issue(Long userId, String email, String role);
}