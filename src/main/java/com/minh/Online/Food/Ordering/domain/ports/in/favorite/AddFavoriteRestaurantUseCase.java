package com.minh.Online.Food.Ordering.domain.ports.in.favorite;

public interface AddFavoriteRestaurantUseCase {
    void addFavorite(Long userId, Long restaurantId);
}

