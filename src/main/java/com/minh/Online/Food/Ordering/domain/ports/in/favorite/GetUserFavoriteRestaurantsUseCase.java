package com.minh.Online.Food.Ordering.domain.ports.in.favorite;

import java.util.List;

public interface GetUserFavoriteRestaurantsUseCase {
    /**
     * Trả về list restaurantId mà user đã favorite
     */
    List<Long> getFavoriteRestaurantIds(Long userId);
}