package com.minh.Online.Food.Ordering.domain.ports.in.favorite;

import java.util.List;

public interface RemoveFavoriteRestaurantUseCase {
    void removeFavorite(Long userId, Long restaurantId);
}
