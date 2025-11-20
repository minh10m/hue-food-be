package com.minh.Online.Food.Ordering.domain.ports.in.favorite;

public interface RemoveFavoriteRestaurantUseCase {
    void removeFavorite(Long userId, Long restaurantId);
}
