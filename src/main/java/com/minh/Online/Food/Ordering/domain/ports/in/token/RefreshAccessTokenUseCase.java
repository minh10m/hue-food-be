package com.minh.Online.Food.Ordering.domain.ports.in.token;

public interface RefreshAccessTokenUseCase {
    String refresh(String refreshToken);
}